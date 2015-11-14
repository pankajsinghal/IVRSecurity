<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="css/playlist.css" />
<script type="text/javascript" src="mxgraph/js/jkl-dumper.js"></script>
<script type="text/javascript" src="mxgraph/js/ObjTree.js"></script>
<script type="text/javascript" src="mxgraph/js/demo.js"></script>
<script type="text/javascript" src="mxgraph/js/FileSaver.js"></script>


<script type="text/javascript">
	var playlistContentArray = null;
	var deletecontent = null;
	var fileInfo = null;

	/**
	* Function : moveUp
	* 
	* Helps the user to move single/multiple List value(s) upwards
	*/
	function moveUp()
	{
		var ddl = document.getElementById('playcontentselect');

		var selectedItems = new Array();
		for(var i = 0; i < ddl.length; i++)
			if(ddl.options[i].selected)				
				selectedItems.push(i);
			
		if(selectedItems.length > 0)	
			if(selectedItems[0] != 0)
				for(var i = 0; i < selectedItems.length; i++)
				{
					var temp_innerHTML = ddl.options[selectedItems[i]].innerHTML;
					var temp_value = ddl.options[selectedItems[i]].value;
					ddl.options[selectedItems[i]].innerHTML = ddl.options[selectedItems[i] - 1].innerHTML;
					ddl.options[selectedItems[i]].value = ddl.options[selectedItems[i] - 1].value;
					ddl.options[selectedItems[i] - 1].innerHTML = temp_innerHTML;	
					ddl.options[selectedItems[i] - 1].value = temp_value;	
					ddl.options[selectedItems[i] - 1].selected = true;
					ddl.options[selectedItems[i]].selected = false;
				}
	}
	
	/**
	* Function : moveDown
	* 
	* Helps the user to move single/multiple List value(s) downwards
	*/
	function moveDown()
	{
		var ddl = document.getElementById('playcontentselect');

		var selectedItems = new Array();
		for(var i = 0; i < ddl.length; i++)
			if(ddl.options[i].selected)				
				selectedItems.push(i);
			
		if(selectedItems.length > 0)	
			if(selectedItems[selectedItems.length - 1] != ddl.length - 1)
				for(var i = selectedItems.length - 1; i >= 0; i--)
				{
					var temp_innerHTML = ddl.options[selectedItems[i]].innerHTML;
					var temp_value = ddl.options[selectedItems[i]].value;
					ddl.options[selectedItems[i]].innerHTML = ddl.options[selectedItems[i] + 1].innerHTML;
					ddl.options[selectedItems[i]].value = ddl.options[selectedItems[i] + 1].value;
					ddl.options[selectedItems[i] + 1].innerHTML = temp_innerHTML;	
					ddl.options[selectedItems[i] + 1].value = temp_value;	
					ddl.options[selectedItems[i] + 1].selected = true;
					ddl.options[selectedItems[i] + 1].selected = true;
					ddl.options[selectedItems[i]].selected = false;
				}			
	}
	
	/**
	* Function : populatePlaylist
	* 
	* This function fills the playlistcontent listbox with the corresponding values of the playlist selected 
	*/
	function populatePlaylist() {
		var playlistcontent = document.getElementById("playcontentselect");
		var playlist = document.getElementById("playselect");
		if (playlistcontent.options.length > 0)
			//if(confirm("All Playlist content will be lost. Are you sure?"))
			while (playlistcontent.options.length != 0)
				playlistcontent.remove(0);

		var xmlhttp_files;
		var returntext = null;

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4 && xmlhttp_files.status == 200) {
				returntext = xmlhttp_files.responseText;
				playlistcontent.innerHTML = returntext;
				playlistContentArray = new Array();

				for (var i = 0; i < playlistcontent.length; i++)
					playlistContentArray.push(playlistcontent.options[i].value);
			}

		}		

		var index = playlist.options[playlist.selectedIndex].value;
		var url = 'getPlayLstContent/' + index;
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);
	}

	/**
	* Function : parseJson
	* 
	* This is a stub function which helps parsing the filestore JSON
	*/
	function parseJson() {
		var json_obj = document.getElementById("jsonFileStore").value;
		var json_obj_files = JSON.parse(json_obj);
		parseFileXml(json_obj_files, "json_obj_files.filestore.root");
	}

	/**
	* Function : parseFileXml
	* 
	* This function parses the json and adds the selected values into the playlist content listbox
	*/
	function parseFileXml(json_obj_files, json_seq) {
		var folders = Object.keys(eval(json_seq));

		for (var i = 0; i < folders.length; i++)
			if (folders[i] != '__wavs__')
				parseFileXml(json_obj_files, json_seq + '.' + folders[i]);
			else {
				var JsonPathInfo = eval(json_seq + '.__wavs__.wav');
				var iframeControlObj = document.getElementById('iframe');
				var playlistcontents = document
						.getElementById("playcontentselect");
				for (var j = 0; j < JsonPathInfo.length; j++) {
					if (JsonPathInfo[j].id != '-1') {
						var check_data = (iframeControlObj != null) ? iframeControlObj.contentDocument
								.getElementById(JsonPathInfo[j].id)
								: ((JsonPathInfo[j].checked == 'true') ? true
										: false);

						if ((typeof check_data == 'object') ? check_data.checked
								: check_data) {
							var option = document.createElement('option');
							option.value = JsonPathInfo[j].id + '-';
							option.innerHTML = JsonPathInfo[j].path;
							playlistcontents.appendChild(option);
						}
					}
				}
			}
	}

	/**
	* Function : showPlayName
	* 
	* This function shows the Add New Playlist popup
	*/
	function showPlayName() {
		var playlist = document.getElementById("playlistNewDiv");
		document.getElementById("playlstnm").value = "";
		playlist.style.display = "block";
		outerdiv.style.display = "block";

		document.getElementById("playlstnm").focus();
	}

	
	
	/**
	* Function : addPlayName
	* 
	* This function adds new playlist in the playlist listbox
	*/
	function addPlayName() {
		var playselect = document.getElementById("playselect");
		var playlist = document.getElementById("playlistNewDiv");
		var option = document.createElement("option");
		if (document.getElementById("playlstnm").value == "") {
			alert("Enter Playlist Name");
			return;
		}

		var xmlhttp_files;
		var returntext = null;

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4 && xmlhttp_files.status == 200) {
				returntext = xmlhttp_files.responseText; 
				//alert(returntext);
				if (returntext == "-1") {
					alert("Playlist already exists. Please Choose another name");
				} 
				else if(returntext == "-2"){
					alert("SQL Exception has occured. Please check logs");
				}
					
				else {
					option.value = returntext;
					option.innerHTML = document.getElementById("playlstnm").value;
					playselect.appendChild(option);
					playlist.style.display = "none";
					outerdiv.style.display = "none";
				}

			}
		}

		var url = 'addPlayList/' + document.getElementById("playlstnm").value;
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);

	}


	/**
	* Function : cancelPopup
	* 
	* This function cancels the addition of new playlist
	*/
	function cancelPopup() {
		var playlist = document.getElementById("playlistNewDiv");
		playlist.style.display = "none";
		outerdiv.style.display = "none";
	}
	
	
	function cancelPlaylistPopup(){
		var playlist = document.getElementById("uploadFile");
		playlist.style.display = "none";
		outerdiv.style.display = "none";		
	}

	/**
	* Function : delPlayName
	* 
	* This function deletes a playlist (Not yet fully implemented)
	*/
	function delPlayName() {
		var playselect = document.getElementById("playselect");

		if (playselect.length > 0) {
			if (confirm("You are deleting " + playselect.length
					+ " contents. Are you sure ?")) {				
						playselect.remove(i--);
			}
		}
	}


	/**
	* Function : delPlaylistContentName
	* 
	* This function deletes the contents of the playlist (single/multiple)
	*/
	function delPlaylistContentName() {
		var playcontentselect = document.getElementById("playcontentselect");
		var splice_content;
		var count = 0;
		
		for (var i = 0; i < playcontentselect.length; i++) {
			if (playcontentselect.options[i].selected) {
				count++;
			}
		}
		deletecontent = new Array();
		if (playcontentselect.length > 0) {
			if (confirm("You are deleting " + count
					+ " content(s). Are you sure ?")) {
				for (var i = 0; i < playcontentselect.length; i++) {
					if (playcontentselect.options[i].selected) {
						if (playcontentselect.options[i].value.split('-')[1] != '') {
							for (var ii = 0; ii < playlistContentArray.length; ii++)
								if (playlistContentArray[ii] == playcontentselect.options[i].value) {
									splice_content = playlistContentArray.splice(ii,1);
									deletecontent.push(splice_content[0].split('-')[1])
									break;
								}
						}
					}
				}

				for (var i = 0; i < playcontentselect.length; i++) {
					if (playcontentselect.options[i].selected) {
						playcontentselect.remove(i--);
					}

				}

			}
		}
	}

	/**
	* Function : getContentList
	* 
	* This function gets the contents of the filestore from the database and shows 
	* them in tree structure
	*/
	function getContentList() {

		var xmlhttp_files;
		var returntext = null;

		document.getElementById('onloading').style.display = 'block';

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4 && xmlhttp_files.status == 200) {
				var json_files_xmlFileData;
				var json_obj_files_xmlFileData;
				returntext = xmlhttp_files.responseText;

				var iframeControlObj = document.getElementById("iframe");

				var file_xml = document.getElementById('hiddenxml');
				file_xml.value = xmlhttp_files.responseText; 
				var file_json = document.getElementById("jsonFileStore");
				file_xml.value = xmlhttp_files.responseText;
				json_files = formatterConverter.prototype
						.xmltojson(xmlhttp_files.responseText);
				json_obj_files = JSON.parse(json_files);
				file_json.value = json_files;
				iframeControlObj.contentWindow.location.reload();
				document.getElementById('onloading').style.display = 'none';
			}
		}

		var url = 'getContentList';
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);
	}

	/**	
	* 
	* Executes this function after the page has loaded 
	*
	*/
	document.addEventListener('DOMContentLoaded', function() {
		getContentList();
	}, false);

	/**
	* Function : savePlaylist
	* 
	* This function saves the playlist
	*/
	function savePlaylist() {
		var playselect = document.getElementById("playselect");
		var playlistid = playselect.options[playselect.selectedIndex].value;
		var playcontentselect = document.getElementById("playcontentselect");
		var playcontentarray = new Array();
		var flag = true;
		var nullArray = new Array();
		var insertcontent = new Array();
		nullArray.push("-1");


		for (var i = 0; i < playcontentselect.length; i++) 
			insertcontent.push(playcontentselect.options[i].value.split('-')[0]);
		var xmlhttp_files;
		var returntext = null;

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4 && xmlhttp_files.status == 200) {
				deletecontent = new Array();
				returntext = xmlhttp_files.responseText;
				if(returntext == 0)
					alert("Error Occured while saving");
				else					
					alert("Playlist saved successfully");
			}
		}

		//var url = 'savePlayListContents/' + playlistid + '/' + ((deletecontent != null && deletecontent.length > 0) ? deletecontent : nullArray) + '/' + ((playcontentarray != null && playcontentarray.length > 0) ? playcontentarray : nullArray);
		var url = 'savePlayListContents/' + playlistid + '/' + ((insertcontent != null && insertcontent.length > 0) ? insertcontent : nullArray);
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);
	}
	
	
	function exportList()
	{
		var ddl = document.getElementById('playselect');

		var count = 0;
		
		for (var i = 0; i < ddl.length; i++) {
				if (ddl.options[i].selected) {
					count++;
				}
			}

		if (count == 0) {
			alert("Please select a playlist");
			return;
		}
		var selectedItems = new Array();
		for (var i = 0; i < ddl.length; i++)
			if (ddl.options[i].selected)
				selectedItems.push(ddl.options[i].value);

		var xmlhttp_files;
		var returntext = null;

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4 && xmlhttp_files.status == 200) {
				returntext = xmlhttp_files.responseText;
				//alert(returntext);
				var blobxml = new Blob([ returntext ], {
					type : "text/plain;charset=" + "UTF-8"
				});
				saveAs(blobxml, 'Playlist' + ".txt");
			}
		}

		var url = 'exportPlayList/' + selectedItems;
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);
	}
	
	
	
	/**
	* Function : importPlayList
	* 
	* This function shows the Add New Playlist popup
	*/
	
	function importPlayList(xml) 
	{
		var playlist = document.getElementById("importFile");

		var reader = new FileReader();		
		reader.readAsText(playlist.files[0]);
		
			
		if (confirm("Are you sure ?")) {
	
				var xml = encodeURIComponent(reader.result);
	
				var xmlhttp_files;
				var returntext = null;
	
				if (window.XMLHttpRequest) {
					xmlhttp_files = new XMLHttpRequest();
				} else {// code for IE6, IE5
					xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
				}
	
				xmlhttp_files.onreadystatechange = function() {
	
					if (xmlhttp_files.readyState == 4
							&& xmlhttp_files.status == 200) {
						returntext = xmlhttp_files.responseText;
						alert(returntext);
						var playselect = document.getElementById("playselect");
						var playselectcontent = document.getElementById("playcontentselect");

						cancelPlaylistPopup();
						
						for(var i = 0; i < playselect.length; i++)
							playselect.remove(playselect[i]);
						
						for(var i = 0; i < playselectcontent.length; i++)
							playselectcontent.remove(playselectcontent[i]);
						
						getPlaylist_onImport();
					}
				}
	
				var url = 'importPlaylist';
				xmlhttp_files.open("POST", url, true);
				xmlhttp_files.send(xml);
			}
	
		}
	
	/**
	 * Function : showImport
	 * 
	 * This function Imports a new playlist from XML file
	 */
	function getPlaylist_onImport() {

		var xmlhttp_files;
		var returntext = null;

		if (window.XMLHttpRequest) {
			xmlhttp_files = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp_files = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp_files.onreadystatechange = function() {

			if (xmlhttp_files.readyState == 4
					&& xmlhttp_files.status == 200) {
				returntext = xmlhttp_files.responseText;
				var playselect = document.getElementById("playselect");
				playselect.innerHTML = returntext;
			}
		}

		var url = 'getPlaylist_onImport';
		xmlhttp_files.open("GET", url, true);
		xmlhttp_files.send(null);
	}
	
		
	/**
	 * Function : showImport
	 * 
	 * This function Imports a new playlist from XML file
	 */
	function showImport() {
		var playlist = document.getElementById("uploadFile");
		document.getElementById("importFile").value = "";
		playlist.style.display = "block";
		outerdiv.style.display = "block";
	}

	function getFileContent(uploadFile) {
		var reader = new FileReader();
		reader.readAsDataURL(uploadFile.files[0]);
		fileInfo = reader.result;
	}
	/**
	 * Function : validate
	 * 
	 * This function validates the characters in the playlist
	 */
	function validate(playlstnm) {
		var regex = /[^a-zA-Z0-9_]/ig;
		playlstnm.value = playlstnm.value.replace(regex, "");

	}

	/**
	 * Function : formatterConverter
	 * 
	 * This is a XML -> JSON and JSON -> XML conversion function
	 */
	function formatterConverter() {
	}

	formatterConverter.prototype.xmltojson = function(xml) {
		var xotree = new XML.ObjTree();
		var dumper = new JKL.Dumper();
		var tree = xotree.parseXML(xml);

		return (dumper.dump(tree));
	};

	formatterConverter.prototype.jsontoxml = function(json) {
		var xotree = new XML.ObjTree();
		var jsontext = JSON.parse(json);

		var xml = (xotree.writeXML(jsontext));

		return (xml);
	};
</script>
<style>
#playlistNewDiv {
	position: relative;
	top: 0;
	left: 284px;
	display: none;
	top: -312px;
	left: 209px;
	float: left;
	padding: 10px 18px;
}

#firstPopupDivInner {
	height: 100%;
	vertical-align: middle;
	margin: 0 auto;
	float: left;
	text-align: right;
}

#uploadFile {
	position: relative;
	top: 0;
	left: 284px;
	display: none;
	top: -312px;
	left: 209px;
	float: left;
	padding: 10px 18px;
}

#secondPopupDivInner {
	height: 100%;
	vertical-align: middle;
	margin: 0 auto;
	float: left;
	text-align: right;
}

#outerdiv {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	display: none;
	background: #000;
	opacity: .25;
}

#onloading {
	position: absolute;
	z-index: 999;
	display: none;
}

#onloading div {
	display: table-cell;
	width: 497px;
	height: 451px;
	background: #fff;
	text-align: center;
	vertical-align: middle;
}
</style>

<div id="outerdiv"></div>


<!-- <h3 id="statusview"
	style="color: #1f1f23; font-size: 30px; font-weight: bold; text-align: center;">
</h3> -->

<div id="maindiv" class="myform">
	<table>
		<tr>
			<td style="text-align: right"><form:form method="POST"
					commandName="playlistForm">
					<table>
						<tr>
							<td>
								<div id="playlistdiv" class="playlist">
									<h1>Playlists</h1>
									<form:select id="playselect" path="playlist"
										multiple="multiple" onchange="populatePlaylist();">
										<c:forEach items="${playList}" var="list">
											<option value="${list.playlist_id}">
												<c:out value="${list.playlist_name}" />
											</option>
										</c:forEach>
									</form:select>
							</td>
							</div>
							</td>
						</tr>
						<tr>
							<td>
							
							<input id="deletePlaylist" class="btnplaylist"
								type="button" style="visibility: hidden" value="Delete Playlist"
								onclick="delPlayName();" /> <input id="addPlaylist"
								class="btnplaylist" type="button" value="New Playlist"
								onclick="showPlayName();" style="float:right;"/>
								<img class="btnplaylist" style="width : 18px; height:18px;float:right;margin-left:5px;" src="mxgraph/images/export.png" onclick="javascript:exportList();"/>
								<img class="btnplaylist" style="width : 18px; height:18px;float:right;margin-left:5px;" src="mxgraph/images/import.png" onclick="javascript:showImport();" />
								</td>
						</tr>
					</table>
				</form:form></td>
			<td style="text-align: right">&nbsp;</td>
			<td rowspan="2" style="vertical-align: top">
				<table>
					<tr>
						<td>
							<div id="contentdiv" class="playlist">
								<h1>Contents</h1>
								<div id="onloading">
									<div>
										<img src="mxgraph/images/loading.gif" />
									</div>
								</div>
								<iframe id="iframe" src="mxgraph/tree.html"></iframe>
							</div>
						</td>
					</tr>
					<tr style="text-align: right">

						<td><input id="addContent" class="btnplaylist" type="button"
							value="Add To Playlist" onclick="parseJson();" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="text-align: right">
				<table>
					<tr>
						<td>
							<div id="playlistcontentdiv" class="playlist">
								<h1>Playlist Songs</h1>
								<!-- playlistcontent -->
								<select id="playcontentselect" multiple="multiple">
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: left; float: left;">&nbsp;</td>
						<td style="text-align: right; float: right;"><input
							id="savePlaylist" class="btnplaylist" type="button" value="Save"
							onclick="savePlaylist();" /></td>
					</tr>
				</table>
			</td>
			<td style="text-align: center"><img
				onclick="javascript:moveUp();" src="img/Playup.png" /><br> <img
				onclick="javascript:delPlaylistContentName();" src="img/PlayDel.png" /><br>
				<img onclick="javascript:moveDown();" src="img/Playdown.png" /></td>
		</tr>
	</table>

	<input type="hidden" id="jsonFileStore" />

	<div id="playlistNewDiv" class="playlist">
		<div id="firstPopupDivInner">
			<table>
				<tr>
					<td>Play List Name</td>
					<td><input id="playlstnm" type="text"
						onkeyup="validate(this);" /></td>
				</tr>
				<tr>
					<td colspan="2"><input id="newPlayList"
						class="btnplaylistnopadding" type="button" value="Add"
						onclick="addPlayName();" /><input id="cancelPlayList"
						class="btnplaylistnopadding" type="button" value="Cancel"
						onclick="cancelPopup();" /></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="uploadFile" class="playlist">
		<div id="secondPopupDivInner">
			<table>
				<tr>
					<td>Upload File</td>
					<td><input id="importFile" type="file" accept="application/xml" onchange="getFileContent(this);"/></td>
				</tr>
				<tr>
					<td colspan="2"><input id="btnOkImport"
						class="btnplaylistnopadding" type="button" value="OK"
						onclick="importPlayList();" /><input id="btnCancelImport"
						class="btnplaylistnopadding" type="button" value="Cancel"
						onclick="cancelPlaylistPopup();" /></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<input type="hidden" id="hiddenxml" />