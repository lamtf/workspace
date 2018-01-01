fs = require("fs")

console.log("starting the javascript code..");


var data = "hello from javascript";

fs.writeFile("teste2.txt", data, function(err){
	if(err){
		console.log(err.toString());
	}
	console.log("done");
});