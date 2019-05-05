<!DOCTYPE HTML><html lang="en">
<head>
    <meta charset="UTF-8">
    <title>File Chooser and Viewer</title>
</head>
<body>
<p>
<br>
<h2 align="center"> Choose a category and a file to view </h2>
<form action="FileChooserViewertest" id="myform">
    <h2 align="center">
        <div><a onclick="hidetxt('id1'); return false;" href="#" rel="nofollow">
            <font color="green"> Poetry </font> </a>
    </h2>
    <div id="id1" style="display: none">
        <p>
        <h3 align="center">
            <input type="radio" name="poetry" value="Poetry0"> Poetry0 <br>
            <input type="radio" name="poetry" value="Poetry1"> Poetry1 <br>
            <input type="radio" name="poetry" value="Poetry2"> Poetry2 <br>
        </p>
    </div>
    <!-- <br><input type="submit" value="open">  -->
    </div>
    <h2 align="center">
        <div><a onclick="hidetxt('id2'); return false;" href="#" rel="nofollow">
            <font color="green"> Sci-fi </font></a>
    </h2>
    <div id="id2" style="display: none">
        <p>
        <h3 align="center">
            <input type="radio" name="sci-fi" value="Sci-fi0"> Sci-fi0 <br>
            <input type="radio" name="sci-fi" value="Sci-fi1"> Sci-fi1 <br>
            <input type="radio" name="sci-fi" value="Sci-fi2"> Sci-fi2 <br>
            <input type="radio" name="sci-fi" value="Sci-fi3"> Sci-fi3 <br></p>
        </p>
    </div>
    <!--  <br><input type="submit" value="open">  -->
    </div>
    <h2 align="center">
        <div><a onclick="hidetxt('id3'); return false;" href="#" rel="nofollow">
            <font color="green"> Fantasy </font></a>
    </h2>
    <div id="id3" style="display: none">
        <p>
        <h3 align="center">
            <input type="radio" name="fantasy" value="Fantasy0"> Fantasy0 <br>
            <input type="radio" name="fantasy" value="Fantasy1"> Fantasy1 <br>
            <input type="radio" name="fantasy" value="Fantasy2"> Fantasy2 <br></p>
        </p>
    </div>
    <!--  <br><input type="submit" value="open">  -->
    </div>
    <div style="text-align:center;">
        <input type="submit" value="view"/>
    </div>
</form>
</body>
</html>

<script>
    var show;
    function hidetxt(type) {
        document.getElementById("myform").reset();
        param = document.getElementById(type);
        if (param.style.display == "none") {
            if (show) show.style.display = "none";
            param.style.display = "block";
            show = param;
        } else param.style.display = "none"
    }
</script>