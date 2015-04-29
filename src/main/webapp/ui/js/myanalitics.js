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

function createQueryString(data) {
	var query = '?';
	for (var key in data) {
		query += key + "=" + data[key] + "&";
	}
	return query;
}

function makeCrossRequest(url, data) {
	isCurrentlyRunning = true;
	
	var src = url + createQueryString(data);
	var scriptElement = createScriptElement(CROSS_SCRIPT_ID, src);
	$("head").first().append(scriptElement);
	var response = $(scriptElement).text();
	//$(scriptElement).remove();

	isCurrentlyRunning = false;
	
	return response;
}

function createScriptElement(id, src) {
	var scriptElement = $("<script>");
	$(scriptElement).attr("id", id);
	$(scriptElement).attr("src", src);
	return scriptElement;
}