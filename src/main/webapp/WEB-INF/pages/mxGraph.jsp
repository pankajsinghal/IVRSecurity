
<!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=5,IE=9" ><![endif]-->
	<%
		Cookie cookie = null;
		Cookie[] cookies = null;
		// Get an array of Cookies associated with this domain
		cookies = request.getCookies();
		if (cookies != null) {
			/* out.println("<h2> Found Cookies Name and Value</h2>"); */
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if ((cookie.getName()).compareTo("xmlsent") == 0) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
					/* out.print("Deleted cookie: " + cookie.getName()
							+ "<br/>"); */
				}
				/* out.print("Name : " + cookie.getName() + ",  ");
				out.print("Value: " + cookie.getValue() + " <br/>"); */
			}
		} else {
			/* out.println("<h2>No cookies founds</h2>"); */
		}
	%>
<!DOCTYPE html>
<html>
<head>
<title>Graph Editor</title>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="mxgraph/styles/grapheditor.css">
<script type="text/javascript">
	// Public global variables

	var MAX_REQUEST_SIZE = 209715200;
	var MAX_WIDTH = 10000;
	var MAX_HEIGHT = 10000;


	// URLs for save and export
	var EXPORT_URL = 'export.htm';
	var SAVE_URL = 'save.htm';
	var TEMPLATE_URL = 'saveTemplate.htm';
	var OPEN_TEMPLATE_GET_SERVICE_URL = 'openTemplateGetService.htm';
	var OPEN_TEMPLATE_GET_XML_URL = 'openTemplateGetXml.htm';
	var OPEN_URL = '/open';
	var RESOURCES_PATH = 'mxgraph/resources';
	var RESOURCE_BASE = RESOURCES_PATH + '/grapheditor';
	var STENCIL_PATH = 'mxgraph/stencils';
	var IMAGE_PATH = 'mxgraph/images';
	var STYLE_PATH = 'mxgraph/styles';
	var CSS_PATH = 'mxgraph/styles';
	var OPEN_FORM = 'mxgraph/open.html';

	// Specifies connection mode for touch devices (at least one should be true)
	var tapAndHoldStartsConnection = true;
	var showConnectorImg = true;

	// Parses URL parameters. Supported parameters are:
	// - lang=xy: Specifies the language of the user interface.
	// - touch=1: Enables a touch-style user interface.
	// - storage=local: Enables HTML5 local storage.
	var urlParams = (function(url) {
		var result = new Object();
		var idx = url.lastIndexOf('?');

		if (idx > 0) {
			var params = url.substring(idx + 1).split('&');

			for (var i = 0; i < params.length; i++) {
				idx = params[i].indexOf('=');

				if (idx > 0) {
					result[params[i].substring(0, idx)] = params[i]
							.substring(idx + 1);
				}
			}
		}

		return result;
	})(window.location.href);

	// Sets the base path, the UI language via URL param and configures the
	// supported languages to avoid 404s. The loading of all core language
	// resources is disabled as all required resources are in grapheditor.
	// properties. Note that in this example the loading of two resource
	// files (the special bundle and the default bundle) is disabled to
	// save a GET request. This requires that all resources be present in
	// the special bundle.
	mxLoadResources = false;
	mxBasePath = 'mxgraph';
	mxLanguage = urlParams['lang'];
	mxLanguages = [ 'de' ];
</script>
<script type="text/javascript" src="mxgraph/js/mxClient.js"></script>
<script type="text/javascript" src="mxgraph/js/Editor.js"></script>
<script type="text/javascript" src="mxgraph/js/Graph.js"></script>
<script type="text/javascript" src="mxgraph/js/EditorUi.js"></script>
<script type="text/javascript" src="mxgraph/js/Actions.js"></script>
<script type="text/javascript" src="mxgraph/js/Menus.js"></script>
<script type="text/javascript" src="mxgraph/js/Sidebar.js"></script>
<script type="text/javascript" src="mxgraph/js/Toolbar.js"></script>
<script type="text/javascript" src="mxgraph/js/Dialogs.js"></script>
<script type="text/javascript" src="mxgraph/jscolor/jscolor.js"></script>
<script type="text/javascript" src="mxgraph/js/jkl-dumper.js"></script>
<script type="text/javascript" src="mxgraph/js/ObjTree.js"></script>
<script type="text/javascript" src="mxgraph/js/zk.base.js"></script>
<script type="text/javascript" src="mxgraph/js/zk.popupmenu.js"></script>
<script type="text/javascript" src="mxgraph/js/zk.treenode.js"></script>
<script type="text/javascript" src="mxgraph/js/zk.tree.js"></script>
<script type="text/javascript" src="mxgraph/js/demo.js"></script>
<script type="text/javascript" src="mxgraph/js/FileSaver.js"></script>
</head>
<body class="geEditor">
	<script type="text/javascript">
		// Extends EditorUi to update I/O action states
		(function() {
			var editorUiInit = EditorUi.prototype.init;

			EditorUi.prototype.init = function() {
			
			<c:if test="${not empty status}">
				alert(${status});
			</c:if>
				
				editorUiInit.apply(this, arguments);
				this.actions.get('save').setEnabled(enabled);

				// Updates action states which require a backend
				if (!useLocalStorage) {
					mxUtils.post(OPEN_URL, '', mxUtils.bind(this,
							function(req) {
								var enabled = req.getStatus() != 404;
								this.actions.get('open').setEnabled(
										enabled || fileSupport);
								this.actions.get('import').setEnabled(
										enabled || fileSupport);
								this.actions.get('save').setEnabled(enabled);
								this.actions.get('saveAs').setEnabled(enabled);
								this.actions.get('export').setEnabled(enabled);
							}));
				}
			};
		})();

		new EditorUi(new Editor());
		
	</script>
	<div id='cssmenu'>		
		<input type="hidden" id="hiddenxml" />
		<li><a href="welcome.htm"><span>Back</span></a> <a
			href="j_spring_security_logout" class="ui-btn-right"> <span>Logout</span></a>
		</li>
		<div id="oval_parent">
			<div id="oval">
				<img src="mxgraph/images/bng.jpeg" width="90" height="36">
			</div>
		</div>
	</div>
</body>
</html>