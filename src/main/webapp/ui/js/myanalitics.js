var ANALITICS_HOST = "http://myanalitics.info:8080/webanalitics";
var CROSS_SCRIPT_ID = "CROSS_SCRIPT_ID";
var CROSS_SCRIPT_CALLBACK_NAME = "crossScriptCallback";

var isCurrentlyRunning = false;

var documentWasReadyTime = undefined;

//initializeJQuery();

$(document).ready(function() {
	documentWasReadyTime = Date.now();
});

$(window).load(function() {
	var windowReadyTime = Date.now();
	var timing = window.performance.timing;
	var data = {
		location : document.URL,
		referrer : document.referrer,
		time : new Date(),
		cookieEnabled : isCookieEnabled(),
		responseTime : timing.responseEnd - timing.responseStart,
		domTime : timing.domComplete - timing.domLoading,
		resourcesTime : windowReadyTime - documentWasReadyTime
	};
	sendPageInfo(data);
});

function isCookieEnabled() {
	return navigator.cookieEnabled;
}

function initializeJQuery() {
	if (!window.jQuery) {
		var script = document.createElement("script");
		script.src = "//code.jquery.com/jquery-1.11.2.min.js";
		script.type = "text/javascript";
		document.head.appendChild(script);
	}
}
/*
 * function sendCurrentPageInfoAjax() {
 * 
 * $.ajax({ url : ANALITICS_HOST + "/ajax/pageInfo", dataType : "jsonp", jsonp :
 * "callback", type : "get", async : false, data : data, complete : function() {
 * console.log("jsonp completed"); } }); }
 */

function sendPageInfo(data) {
	var url = ANALITICS_HOST + "/ajax/pageInfo";
	makeCrossRequestWithScript(url, data, null);
}

function getServerTime(handler) {
	var url = ANALITICS_HOST + "/ajax/getServerTime";
	var res = makeCrossRequestWithScript(url, null, handler);
}

function determineSessionId() {
	//vbsessionhash
	//
}

function createQueryString(data) {
	var query = "?callback=" + CROSS_SCRIPT_CALLBACK_NAME;
	if (data !== undefined && data !== null) {
		for ( var key in data) {
			query += "&" + key + "=" + data[key];
		}
	}
	return query;
}

function makeCrossRequestWithScript(url, data, handler) {
	isCurrentlyRunning = true;

	var src = url + createQueryString(data);
	var scriptElement = createScriptElement(CROSS_SCRIPT_ID, src);
	$("head").first().append(scriptElement);

	/*
	waitLoadAndThenCallback(scriptElement, function() {
		var callbackInScript = window[CROSS_SCRIPT_CALLBACK_NAME];
		var response = callbackInScript !== undefined ? callbackInScript() : null;
		$(this).remove();
		isCurrentlyRunning = false;

		handler(response);
	});
	*/
}

function waitLoadAndThenCallback(element, callback) {
	/*if (element.readyState !== undefined) {
		waitLoadAndThenCallbackForIE(element, callback);
	} else {
	*/
	/*
		if (!!element.attachEvent) {
		    //Old IE
			element.attachEvent("onload", function(){
		        // script has loaded in IE 7 and 8 as well.
		        callBack();
		    });
		}
		else if (!!element.addEventListener)
		{
			element.addEventListener("load", function() {
		        // Script has loaded.
		        callBack();
		    });
		} else {
			element.onload = function() {
				callBack();
			}
		}
	*/
	$(element).load(function() {
		callBack();
	});
	
}

function waitLoadAndThenCallbackForIE(element, callback) {
	
	if (element.readyState == 'loaded'
			|| element.readyState == 'completed') {
		callback();
	} else {
		setTimeout(function() {
			alert(element.readyState);
			waitLoadAndThenCallbackForIE(element, callback);
		}, 100);
	}
}

function createScriptElement(id, src) {
	var scriptElement = document.createElement("script");
	scriptElement.id = id;
	scriptElement.src = src;
	return scriptElement;
}
