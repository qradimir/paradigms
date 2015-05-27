function addImpl(a, b) { return a + b }
function subImpl(a, b) { return a - b }
function mulImpl(a, b) { return a * b }
function divImpl(a, b) { return a / b }
function modImpl(a, b) { return a % b }

function negImpl(a) { return -a }

function op(f) {
    return function() {
        var ops = arguments;
        return function () {
            return f.apply(null, map(ops, arguments));
        }
    }
}

function map(o, a) {
    var ans = [];
    for (var i in o) {
        ans.push(o[i].apply(null, a));
    }
    return ans;
}

function cnst(value) {
    return function() {
        return value;
    }
}

var vars = []
vars["x"] = 0;
vars["y"] = 1;
vars["z"] = 2;

function variable(name) {
    var i = vars[name];
    return function () { return arguments[i] }
}

var add      = op(addImpl);
var subtract = op(subImpl);
var multiply = op(mulImpl);
var divide   = op(divImpl);
var power    = op(Math.pow);
var mod      = op(modImpl);

var negate = op(negImpl);
var abs    = op(Math.abs);
var log    = op(Math.log);

var ops = [];
ops["+"]  = {n : 2, f : add};
ops["-"]  = {n : 2, f : subtract};
ops["*"]  = {n : 2, f : multiply};
ops["/"]  = {n : 2, f : divide};
ops["**"] = {n : 2, f : power};
ops["%"]  = {n : 2, f : mod};
ops["negate"] = {n : 1, f : negate};
ops["log"]    = {n : 1, f : log};
ops["abs"]    = {n : 1, f : abs};

function parse(expr) {
    var lexems = expr.split(/\s+/);
    var stack = [];
    function foldLast(n, f) {
        a = new Array(n);
        for (var i = n - 1; i >= 0; i--) {
            a[i] = stack.pop();
        }
        stack.push(f.apply(null, a));
    }
    for(var i = lexems[0] == "" ? 1 : 0; i < lexems.length; i++) {
        if (ops[lexems[i]] != undefined) {
            foldLast(ops[lexems[i]].n, ops[lexems[i]].f)
        } else if (vars[lexems[i]] != undefined) {
            stack.push(variable(lexems[i]))
        } else {
            stack.push(cnst(+lexems[i]))
        }
    }
    return stack[0];
}
