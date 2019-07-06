let audioLine;

function initAudio(){
	audioLine = document.createElement("audio");
	audioLine.autoplay = "autoplay";
	document.getElementsByTagName("body")[0].appendChild(audioLine);
}

function setAudio(trackAddress) {
	if(audioLine == null){
		initAudio();
	}
	audioLine.src = trackAddress;
}

function pauseAudio(){
	audioLine.pause();
}

function resumeAudio(){
	audioLine.play();
}