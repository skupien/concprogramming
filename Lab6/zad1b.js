import waterfall from 'async/waterfall.js';

function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
 }
 
 function task1(cb) {
     printAsync("1", function() {
         task2(cb);
     });
 }
 
 function task2(cb) {
     printAsync("2", function() {
         task3(cb);
     });
 }
 
 function task3(cb) {
     printAsync("3", cb);
 }

 
 function loop(n) {
    var arr = [];
    for (var i = 0; i < n; i++) {
        arr = arr.concat(task1);
    };
    waterfall(arr, 
        function() {
            console.log('done!');
        }
    );
 }


 loop(4);