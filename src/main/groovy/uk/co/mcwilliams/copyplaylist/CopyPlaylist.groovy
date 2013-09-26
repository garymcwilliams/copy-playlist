package uk.co.mcwilliams.copyplaylist

import java.nio.file.Files

def playlistDir = new File("D:\\Users\\gmcwilliams\\dropbox\\Personal\\documents\\docs")
def playlistName = "BMW"
def playlistFile = new File(playlistDir, playlistName + ".m3u")

def dstFolder = new File("D:\\temp\\${playlistName}")
dstFolder.mkdirs()

def copiedFiles = []
def existingFiles = []
def invalidFiles = []
def missingFiles = []

playlistFile.eachLine { fileName ->
	if (! (fileName =~ /^#/) ) 
	{
		if (! (fileName =~ /.m4a$/))
		{
			File musicFile = new File(fileName)
			
			if (musicFile.exists())
			{
				def pathParts = musicFile.absolutePath.split("\\\\")
				def artist = pathParts[-3]
				def album = pathParts[-2]
				def track = pathParts[-1]
				File dstMusic = new File(dstFolder, "${artist}\\${album}")
				dstMusic.mkdirs()
				File dstMusicFile = new File(dstMusic, track)
				if (dstMusicFile.exists())
				{
					existingFiles << dstMusicFile.absolutePath
				} else {
					copiedFiles << "$musicFile.absolutePath to $dstMusicFile.absolutePath"
					Files.copy(musicFile.toPath(), dstMusicFile.toPath())
				}
			} else {
				missingFiles << fileName
			}
		} else {
			invalidFiles << fileName
		}
	}
}

println "Copied Files:${copiedFiles.size()}"
File copiedLogfile = new File(dstFolder, "copied.txt")
copiedLogfile.withWriter{ out ->
	copiedFiles.each {out.println it}
}

println "Existing Files:${existingFiles.size()}"
File existingLogfile = new File(dstFolder, "existing.txt")
existingLogfile.withWriter{ out ->
	existingFiles.each {out.println it}
}

println "Invalid Files:${invalidFiles.size()}"
File invalidLogfile = new File(dstFolder, "invalid.txt")
invalidLogfile.withWriter{ out ->
	invalidFiles.each {out.println it}
}

println "Missing Files:${missingFiles.size()}"
File missingLogfile = new File(dstFolder, "missing.txt")
missingLogfile.withWriter{ out ->
	missingFiles.each {out.println it}
}
