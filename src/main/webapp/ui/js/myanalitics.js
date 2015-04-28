var ANALITICS_HOST = 'myanalitics.info:8080/webanalitics'

$(document).ready(function() {
	sendCurrentPageInfo();
});

function sendCurrentPageInfo() {
	var data = {
		location: document.URL,
		referrer: document.referrer, 
		time: new Date()
	}
	$.ajax({
		url: ANALITICS_HOST + '/ajax/pageInfo',
		dataType: "jsonp",
		jsonp: "callback",
		type: 'get',
		async: false,
		data: data,
		complete: function() {
			console.log("jsonp completed");
		}
	});
}

function determineSessionId() {
	
}