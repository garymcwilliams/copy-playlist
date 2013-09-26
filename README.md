copy-playlist
=============

Reads a .m3u playlist file and copied the contained files to a new folder.
The new folder will match the original folder (expected to be in artist/album/track locations)

If files already exist then they are not copied again.
Ignores comments in .m3u file
Ignores m4a files (which don't play in the BMW)

Produces a report and .txt log files of operations