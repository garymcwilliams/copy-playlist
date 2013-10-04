package uk.co.mcwilliams.copyplaylist


sample {
	foo = "default_foo"
	bar = "default_bar"
}

environments {
	bfsproduct1 {
		location {
			m3u = "D:\\Users\\gmcwilliams\\dropbox\\Personal\\documents\\docs"
			playlistname = "BMW"
			dest = "D:\\temp\\${playlistname}"
		}
	}
	acer {
		location {
			m3u = "D:\\Users\\gary\\Documents\\Dropbox\\Personal\\documents\\docs"
			playlistname = "BMW"
			dest = "D:\\temp\\${playlistname}"
		}
	}
}