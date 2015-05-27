function add(a, b) { return a + b }
function sub(a, b) { return a - b }
function mul(a, b) { return a * b }
function div(a, b) { return a / b }
function mod(a, b) { return a % b }
function neg(a) { return -a }

function addD(f, s)  { return new Add(f.ops[0].diff(s), f.ops[1].diff(s)) }
function subD(f, s)  { return new Subtract(f.ops[0].diff(s), f.ops[1].diff(s)) }
function mulD(f, s)  { return new Add(new Multiply(f.ops[0].diff(s), f.ops[1]),
                                      new Multiply(f.ops[0], f.ops[1].diff(s))) }
function divD(f, s)  { return new Divide(new Subtract(new Multiply(f.ops[0].diff(s), f.ops[1]),
                                                      new Multiply(f.ops[0], f.ops[1].diff(s))),
                                         new Multiply(f.ops[1],f.ops[1])) }
function negD(f, s)  { return new Negate(f.ops[0].diff(s)) }
function cosD(f, s)  { return new Multiply(new Negate(new Sin(f.ops[0])), f.ops[0].diff(s))}
function sinD(f, s)  { return new Multiply(new Cos(f.ops[0]), f.ops[0].diff(s)) }
function expD(f, s)  { return new Multiply(new Exp(f.ops[0]), f.ops[0].diff(s)) }
function atanD(f, s) { return new Divide(f.ops[0].diff(s), new Add(new Const(1), new Multiply(f.ops[0], f.ops[0]))) }

function Op(f, name, dif, n) {
    function Constructor() {
        this.ops = toArray(arguments);
    }
    Constructor.prototype.evaluate =
        function() {
            return f.apply(null, this.ops.map(getValue(arguments)));
        };
    Constructor.prototype.toString =
        function() {
            return this.ops.map(getString).join(" ") +" "+ name;
        };
    Constructor.prototype.diff =
        function(name) {
            return dif(this, name);
        };
    Constructor.prototype.prefix =
        function() {
            return "(" + name + " " + this.ops.map(getPrefix).join(" ") + ")";
        };
    Constructor.prototype.n = n;
    operators[name] = Constructor;
    return Constructor;
}

function toArray(args) {
    var r = [];
    r.push.apply(r, args);
    return r;
}

function getValue(args) { return function(expr) { return expr.evaluate.apply(expr, args) ; } }

function getString(expr)     { return expr.toString(); }
function getPrefix(expr)     { return expr.prefix();   }

function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function() { return this.value; };
Const.prototype.toString = function() { return this.value.toString(); };
Const.prototype.diff     = function() { return new Const(0); };
Const.prototype.simplify = function() { return this; };
Const.prototype.prefix   = function() { return this.value.toString(); };

var vars = [];
vars["x"] = 0;
vars["y"] = 1;
vars["z"] = 2;

function Variable(name) {
    this.name = name;
}
Variable.prototype.evaluate = function() { return arguments[vars[this.name]]; };
Variable.prototype.toString = function() { return this.name; };
Variable.prototype.diff     = function(arg) { return this.name == arg ? new Const(1) : new Const(0); };
Variable.prototype.simplify = function() { return this; };
Variable.prototype.prefix   = function() { return this.name; };

var operators = [];

var Add      = Op(add, "+", addD, 2);
var Subtract = Op(sub, "-", subD, 2);
var Multiply = Op(mul, "*", mulD, 2);
var Divide   = Op(div, "/", divD, 2);

var Negate = Op(neg,    "negate",  negD,  1);
var Cos    = Op(Math.cos,  "cos",  cosD,  1);
var Sin    = Op(Math.sin,  "sin",  sinD,  1);
var Exp    = Op(Math.exp,  "exp",  expD,  1);
var ArcTan = Op(Math.atan, "atan", atanD, 1);

function parse(expr) {
    var lexems = expr.split(/\s+/);
    var stack = [];
    function foldLast(f) {
        var n = f.prototype.n;
        a = stack.splice(stack.length - n, n);
        var expr = Object.create(f.prototype);
        f.apply(expr, a);
        stack.push(expr);
    }
    for(var i = lexems[0] == "" ? 1 : 0; i < lexems.length; i++) {
        if (operators[lexems[i]] != undefined) {
            foldLast(operators[lexems[i]]);
        } else if (vars[lexems[i]] != undefined) {
            stack.push(new Variable(lexems[i]));
        } else {
            stack.push(new Const(+lexems[i]));
        }
    }
    return stack[0];
}

function ParsePrefixError(message) {
    this.name = "ParsePrefixError";
    this.message = message;
}
ParsePrefixError.prototype = Error.prototype;

var CBR_SYMBOL = ")";
var numberSymbols = "0123456789";

function parsePrefix(expr) {
    var lexems = [], c, j = 0;
    while ((c = prefixFind()) != undefined) {
        lexems.push(c);
    }
    var i = 0, e  = build();
    if (e == CBR_SYMBOL)   throw new ParsePrefixError("Expression starts with ')'");
    if (e == undefined)    throw new ParsePrefixError("Empty input")
    if (i < lexems.length) throw new ParsePrefixError("Excessive info");
    return e;


    function prefixFind() {
        while (expr[j] == " ") { j++; }
        if (j == expr.length) { return undefined;}

        if (expr[j] == "(" || expr[j] == ")") return expr[j++];
        var start = j;
        while (j < expr.length && expr[j] != " " && expr[j] != ")" && expr[j] != "(") { j++; }
        return expr.substring(start, j);
    }

    function buildOp() {
        var args = [], op = lexems[i], built;
        if (operators[op] == undefined) throw new ParsePrefixError("Invalid operator '" + op + "'");
        i++;
        while ((built = build()) != CBR_SYMBOL) {
            if (built == undefined) throw new ParsePrefixError("Missing close brackets");
            args.push(built);
        }
        if (args.length < operators[op].prototype.n) throw new ParsePrefixError("Not enough arguments after '" + op + "'");
        if (args.length > operators[op].prototype.n) throw new ParsePrefixError("Too many arguments after '" + op +"'");

        var ret = Object.create(operators[op].prototype);
        operators[op].apply(ret, args);
        return ret;
    }

    function toNumber(str) {
        if (str[0] == "+") { throw new ParsePrefixError("Invalid symbol '" + str +"'"); }
        var ret = +str;
        if (isNaN(ret))    { throw new ParsePrefixError("Invalid symbol '" + str +"'"); }
        return ret;
    }

    function build() {
        if (i == lexems.length) return undefined;

        var l = lexems[i];
        i++;

        if (l == "(") return buildOp();
        if (l == ")") return CBR_SYMBOL;
        if (vars[l] != undefined) return new Variable(l);
        return new Const(toNumber(l));
    }
}