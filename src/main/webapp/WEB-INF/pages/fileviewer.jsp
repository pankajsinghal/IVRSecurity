<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>elFinder 2.0</title>
		<!-- jQuery and jQuery UI (REQUIRED) -->
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
		<script src="js/jquery.js"></script>
		<script src="js/jquery-ui.js"></script>

		<!-- elFinder CSS (REQUIRED) and elFinder jQuery-->
		<link rel="stylesheet" type="text/css" href="css/elfinder.css">
		<link rel="stylesheet" type="text/css" href="css/theme.css">
		<script src="js/elfinder.js"></script>

		<!-- elFinder initialization (REQUIRED) -->
		<script type="text/javascript" charset="utf-8">
			// Documentation for client options: https://github.com/Studio-42/elFinder/wiki/Client-configuration-options
			$(document).ready(function() {
				$('#elfinder').elfinder({					
					url : 'connector',					
				});
			});
		</script>
		
	</head>
	<body>
		<!-- Element where elFinder will be created (REQUIRED) -->
		<div id="elfinder"></div>
		
	</body>
</html>