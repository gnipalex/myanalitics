myanalitics = {
	ANALITICS_HOST : "http://myanalitics.info:8080/webanalitics",
	ANALITICS_CONTROLLER : "/collect/pageInfo",
	ACTIVITY_CONTROLLER : "/collect/activity",
	CROSS_SCRIPT_ID : "CROSS_SCRIPT_ID",
	ACTIVITY_SEND_INTERVAL : 20,
	ACTIVITY_ENABLED : true,
	COOKIE_MAX_MINUTES : 30,
	COOKIE_SID_NAME : "myanalitics_sid",
	COOKIE_ACTIVITY_NAME : "myanalitics_activity",

	SESSION_BREAK_PAGE : undefined,
	
	conversionClass : "addToCartButton",
	
	sid : undefined,
	
	saveSid : function(sid) {
		myanalitics.cookie.setCookie(myanalitics.COOKIE_SID_NAME, sid);
		myanalitics.sid = sid;
	},
	
	readSead : function() {
		if (myanalitics.sid === undefined) {
			myanalitics.sid = myanalitics.cookie.readCookie(myanalitics.COOKIE_SID_NAME);
		}
		return myanalitics.sid;
	},
	
	refreshSid : function() {
		myanalitics.saveSid(myanalitics.readSead());
	},
	
	sendPageInfo : function() {
		var url = myanalitics.ANALITICS_HOST + myanalitics.ANALITICS_CONTROLLER;
		var timing = window.performance.timing;
		var sid = myanalitics.readSead();
		var data = {
			location : document.URL,
			referrer : document.referrer,
			cookieEnabled : myanalitics.cookie.isCookieEnabled(),
			responseTime : timing.responseEnd - timing.responseStart,
			domTime : timing.domComplete - timing.domLoading,
			sid: myanalitics.util.isNotEmpty(sid) ? sid : "",
			screenHeight: screen.availHeight,
			screenWidth: screen.availWidth
		};
		myanalitics.request.makeCrossRequestWithScript(url, data, function() {
			myanalitics.request.callbackResult = myanalitics.request.crossCallback();
			
			myanalitics.util.populateConfig();
			
			console.log(myanalitics.callbackResult);
			
			myanalitics.request.removeScriptElement(myanalitics.CROSS_SCRIPT_ID);
			myanalitics.request.crossCallback = undefined;
			myanalitics.request.isCurrentlyRunning = false;
		});
	},
	
	sendActivities : function() {
		var mas = myanalitics.activity.readActivities();
		if (mas.length === 0) {
			return;
		}
		var activitiesJSON = JSON.stringify(mas);
		myanalitics.activity.reset();
		console.out(activitiesJSON);
		var data = {
			json : activitiesJSON,
			sid : myanalitics.readSead()
		}
		var url = myanalitics.ANALITICS_HOST + myanalitics.ACTIVITY_CONTROLLER;
		myanalitics.request.makeCrossRequestWithScript(url, data, function() {
			myanalitics.request.removeScriptElement(myanalitics.CROSS_SCRIPT_ID);
			myanalitics.request.crossCallback = undefined;
			myanalitics.request.isCurrentlyRunning = false;
		});
	},
	
	startListeningActivity : function() { 
		if (!myanalitics.ACTIVITY_ENABLED) {
			return;
		}
		$("a").on("click", function(e) {
			var url = this.href;
			myanalitics.newSessionIfSessionBreakPage(url);
			myanalitics.activity.addActivity(new myanalitics.activity.LinkActivity(this.id, this.href, this.title));
			myanalitics.sendActivities();
		});
		$("button, input[type=button]").on("click", function(e) {
			var caption = undefined;
			if (this.tagName === "BUTTON") {
				caption = this.textContent;
			} else {
				caption = this.value;
			}
			myanalitics.activity.addActivity(new myanalitics.activity.BtnActivity(this.id, caption));
		});
		$("#" + myanalitics.conversionClass + ", ." + myanalitics.conversionClass).on("click", function(e) {
			myanalitics.activity.conversionAction(myanalitics.conversionClass);
			myanalitics.sendActivities();
		});
	},
	
	util : {
		isNotEmpty : function(str) {
			return str !== undefined && str != null && str.length > 0;
		},
		
		populateConfig : function() {
			var src = myanalitics.callbackResult;
			myanalitics.saveSid(myanalitics.util.isNotEmpty(src.sid) 
				? src.sid : "");
			myanalitics.ACTIVITY_ENABLED  = myanalitics.util.isNotEmpty(src.activityListeningEnabled) 
				? src.activityListeningEnabled : myanalitics.ACTIVITY_ENABLED;
			myanalitics.ACTIVITY_SEND_INTERVAL = myanalitics.util.isNotEmpty(src.activitySendInterval)
				? src.activitySendInterval : myanalitics.ACTIVITY_SEND_INTERVAL;
			myanalitics.SESSION_BREAK_PAGE = myanalitics.util.isNotEmpty(src.sessionBreakPage)
				? src.sessionBreakPage : myanalitics.SESSION_BREAK_PAGE;
			myanalitics.conversionClass = myanalitics.util.isNotEmpty(src.conversionClass) 
				? src.conversionClass : myanalitics.conversionClass;
			myanalitics.COOKIE_MAX_MINUTES = myanalitics.util.isNotEmpty(src.cookieMaxTimeMin) 
				? src.cookieMaxTimeMin : myanalitics.COOKIE_MAX_MINUTES;
		}
	},
	
	request : {
		isCurrentlyRunning : false,

		crossCallback : undefined,

		callbackResult : undefined,
		
		createQueryString : function(data) {
			var query = "?";
			if (data !== undefined && data !== null) {
				for ( var key in data) {
					query += "&" + key + "=" + data[key];
				}
			}
			return query;
		},

		createScriptElement : function(id, src) {
			var scriptElement = document.createElement("script");
			scriptElement.id = id;
			scriptElement.src = src;
			return scriptElement;
		},

		removeScriptElement : function(id) {
			var element = document.getElementById(id);
			if (element !== undefined) {
				element.parentNode.removeChild(element);
			}
		},

		makeCrossRequestWithScript : function(url, data, handler) {
			myanalitics.request.isCurrentlyRunning = true;

			var src = url + myanalitics.request.createQueryString(data);
			var scriptElement = myanalitics.request.createScriptElement(
					myanalitics.CROSS_SCRIPT_ID, src);

			$("head").first().append(scriptElement);

			this.waitLoadAndThenHandler(handler);
		},
		
		waitLoadAndThenHandler : function(handler) {
			if (myanalitics.crossCallback === undefined) {
				setTimeout(function() {
					myanalitics.request.waitLoadAndThenHandler(handler);
				}, 100);
				return;
			}
			handler();
		}
	},

	cookie : {
		setCookie : function(name, val) {
			var today = new Date();
			var expire = new Date();
			expire.setTime(today.getTime() + 60000 * myanalitics.cookieMaxMinutes);
			document.cookie = name + "=" + escape(val) + ";expires="
					+ expire.toGMTString();
		},
		
		deleteCookie : function(cookie_name) {
			 var cookie_date = new Date();
			 cookie_date.setTime ( cookie_date.getTime() - 1 );
			 document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
		},

		readCookie : function(cookieName) {
			var re = new RegExp('[; ]' + cookieName + '=([^\\s;]*)');
			var sMatch = (' ' + document.cookie).match(re);
			if (cookieName && sMatch)
				return unescape(sMatch[1]);
			return '';
		},
		
		isCookieEnabled : function() {
			return navigator.cookieEnabled;
		}
	},

	activity : {
		activities : new Array(),
		
		readActivities : function() {
			return this.activities;
		},
		
		reset : function() {
			this.activities = new Array();
		},

		addActivity : function(activity) {
			this.activities.push(activity);
		},

		LinkActivity : function(id, href, title) {
			return {
				tp : "link",
				hr : href,
				ttl : title,
				id : id
			}
		},

		BtnActivity : function(id, caption) {
			return {
				tp : "button",
				ttl : caption,
				id : id
			}
		},
		
		Conversion : function(kind) {
			return {
				tp : "conv",
				knd : kind
			}
		},
		
		conversionAction : function(kind) {
			this.addActivity(new this.Conversion(kind));
		}
	},
	
	newSessionIfSessionBreakPage : function(href) {
		if (myanalitics.util.isNotEmpty(myanalitics.SESSION_BREAK_PAGE)) {
			myanalitics.util.isNotEmpty(href)
			var path = myanalitics.util.isNotEmpty(href) ? href : window.location.pathname;
			if (path.indexOf(myanalitics.SESSION_BREAK_PAGE, 0) > 0) {
				myanalitics.saveSid("");
			}
		}
	}	
}

$(window).load(function() {
	myanalitics.newSessionIfLoginPage();
	myanalitics.sendPageInfo();
	setTimeout(function() {
		if (!myanalitics.ACTIVITY_ENABLED) {
			return;
		}
		myanalitics.startListeningActivity();
		var intervalId = setInterval(function() {
			if (myanalitics.ACTIVITY_ENABLED) {
				myanalitics.sendActivities();
			} else {
				clearInterval(intervalId);
			}
		}, myanalitics.ACTIVITY_SEND_INTERVAL * 1000);
	}, 1000);
});
