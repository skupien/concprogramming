var Fork = function() {
    this.state = 0;
    return this;
}

Fork.prototype.acquire = function(cb) { 
    var delay = 1;
    var self = this;
    (function p() {
        if(self.state == 0) {
            self.state = 1;
            if(cb) cb();
        }
        else {
            if(delay < 10000) delay*=2;
            setTimeout(p, delay);
        }
    })();
}


Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    var currently = 0;
    (function p() {
        var think = Math.floor(Math.random()*1000)+100;
        setTimeout(function() {
            forks[f1].acquire(function() {
                console.log("Philosopher " + id + " acquired 1st fork: " + f1);
                forks[f2].acquire(function() {
                    console.log("Philosopher " + id + " acquired 2nd fork: " + f2);
                    var eat = Math.floor(Math.random()*500)+100;
                    setTimeout(function() {
                        forks[f1].release();
                        forks[f2].release();
                        currently++;
                        console.log("Philosopher " + id +" ate " + currently + " times");
                        if(currently < count) p();
                    }, eat);
                });
            });
        }, think);
    })();

}

Philosopher.prototype.startAsym = function(count, array) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    if(id%2 == 1) {
        [f1, f2] = [f2, f1];
    }

    var waitTime = 0;
    var start = (new Date()).getTime();
    var currently = 0;

    (function p() {
        var think = Math.floor(Math.random()*1000)+100;
        setTimeout(function() {
             start = (new Date()).getTime();
            forks[f1].acquire(function() {
                 waitTime += (new Date()).getTime() - start;
                console.log("Philosopher " + id + " acquired 1st fork: " + f1);
                 start = (new Date()).getTime();
                forks[f2].acquire(function() {
                     waitTime += (new Date()).getTime() - start;
                    console.log("Philosopher " + id + " acquired 2nd fork: " + f2);
                    var eat = Math.floor(Math.random()*500)+100;
                    setTimeout(function() {
                        forks[f1].release();
                        forks[f2].release();
                        currently++;
                        console.log("Philosopher " + id +" ate " + currently + " times");
                        if(currently < count) p();
                        else {
                            array.push(waitTime/count);
                        }
                    }, eat);
                });
            });
        }, think);
    })();

    
}

var Conductor = function(size) {
    this.sem = size-1;
    return this;
}
Conductor.prototype.take = function(cb) {
    var delay = 1;
    var self = this;
    (function d() {
        if(self.sem > 0) {
            self.sem--;
            if(cb) cb();
        }
        else {
            if(delay < 10000) delay*=2;
            console.log(delay);
            setTimeout(d, delay);
        }
    })();
}
Conductor.prototype.give = function() {
    this.sem++;
}
Philosopher.prototype.startConductor = function(count, conductor, array) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    var waitTime = 0;
    var start = (new Date()).getTime();
    var currently = 0;


    (function p() {
        var think = Math.floor(Math.random()*1000)+100;
        setTimeout(function() {
             start = (new Date()).getTime();
            conductor.take(function() {
                waitTime += (new Date()).getTime() - start;
                start = (new Date()).getTime();
                forks[f1].acquire(function() {
                     waitTime += (new Date()).getTime() - start;
                    console.log("Philosopher " + id + " acquired 1st fork: " + f1);
                     start = (new Date()).getTime();
                    forks[f2].acquire(function() {
                         waitTime += (new Date()).getTime() - start;
                        console.log("Philosopher " + id + " acquired 2nd fork: " + f2);
                        var eat = Math.floor(Math.random()*500)+100;
                        setTimeout(function() {
                            forks[f1].release();
                            forks[f2].release();
                            conductor.give();
                            currently++;
                            console.log("Philosopher " + id +" ate " + currently + " times");
                            if(currently < count) p();
                            else {
                                array.push(waitTime/count);
                            }
                        }, eat);
                    });
                });
            });
        }, think);
    })();

}



var N = 5;
var howManyTimes = 10;
var conductor = new Conductor(N);
var forks = [];
var philosophers = [];

var asymAvgTime = [];
var condAvgTime = [];

for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}


for (var i = 0; i < N; i++) {
    // philosophers[i].startNaive(howManyTimes);
    // philosophers[i].startAsym(howManyTimes, asymAvgTime);
    philosophers[i].startConductor(howManyTimes, conductor, condAvgTime);
}

// (function z() {
//     if(asymAvgTime.length == N) {
//         console.log("---------------------------");
//         for (var i = 0; i < N; i++) {
//             philosophers[i].startConductor(howManyTimes, conductor, condAvgTime);
//         }
//         (function x() {
//             if(condAvgTime.length == N) {
//                 var sumA = asymAvgTime.reduce((a,b) => a+b, 0);
//                 var sumC = condAvgTime.reduce((a,b) => a+b, 0);
//                 console.log("Asym: " + Math.round(sumA/asymAvgTime.length));
//                 console.log(asymAvgTime);
//                 console.log("Cond: " + Math.round(sumC/condAvgTime.length));
//                 console.log(condAvgTime);
//             }
//             else {
//                 setTimeout(x, 1000);
//             }
//         })();
//     }
//     else {
//         setTimeout(z, 1000);
//     }
// })();