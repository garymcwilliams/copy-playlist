package uk.co.mcwilliams.copyplaylist

import java.nio.file.Files

Class configClass = getClass().classLoader.loadClass('uk.co.mcwilliams.copyplaylist.Config')
def hostname = Inet4Address.localHost.hostName.replaceAll("-", "")
ConfigObject config = new ConfigSlurper(hostname).parse(configClass)

def playlistDir = new File(config.location.m3u)
def playlistName = config.location.playlistname
def playlistFile = new File(playlistDir, playlistName + ".m3u")

def dstFolder = new File(config.location.dest)
dstFolder.mkdirs()

def copiedFiles = []
def existingFiles = []
def invalidFiles = []
def missingFiles = []
def artistCheck = ""

playlistFile.eachLine { fileName ->
	if (! (fileName =~ /^#/) ) 
	{
//		if (! (fileName =~ /.m4a$/))
//		{
			File musicFile = new File(fileName)
			
			if (musicFile.exists())
			{
				def pathParts = musicFile.absolutePath.split("\\\\")
				def artist = pathParts[-3]
				if (artist != artistCheck)
				{
					println artist
					artistCheck = artist
				}
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
//		} else {
//			invalidFiles << fileName
//		}
	}
}

println "playlist  :$playlistFile"
println "Storing in:$dstFolder"

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
