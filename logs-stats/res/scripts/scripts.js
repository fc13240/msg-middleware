/**
 * Created by Yao on 2016/8/2.
 */

///======================================日志概览模块=============================================
var n = 60;
var random = d3.randomNormal(1000, 200);
var data = d3.range(0, n, 1).map(random);
// console.log(data.length);

var svg = d3.select("#svgLogGenaral"),
    margin = {top: 20, right: 20, bottom: 40, left: 100},
    width = svg.attr("width") - margin.left - margin.right,
    height = svg.attr("height") - margin.top - margin.bottom,
    g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var x = d3.scaleLinear()
    .domain([1, n - 2])
    .range([0, width]);

var y = d3.scaleLinear()
    .domain([500, 1500])
    .range([height, 0]);

var line = d3.line()
    .curve(d3.curveBasis)
    .x(function (d, i) {
        return x(i);
    })
    .y(function (d, i) {
        return y(d);
    });

g.append("defs").append("clipPath")
    .attr("id", "clip")
    .append("rect")
    .attr("width", width)
    .attr("height", height);

g.append("g")
    .attr("class", "axis axis--x")
    .attr("transform", "translate(0," + y(500) + ")")
    .call(d3.axisBottom(x));

g.append("g")
    .attr("class", "axis axis--y")
    .attr("transform", "translate(" + x(0) + ",0)")
    .call(d3.axisLeft(y));

g.append("g")
    .attr("clip-path", "url(#clip)")
    .append("path")
    .datum(data)
    .attr("class", "line")
    .transition()
    .duration(1000)
    .ease(d3.easeLinear)
    .on("start", tick);

function tick() {

    // Push a new data point onto the back.
    data.push(random());

    // Redraw the line.
    d3.select(this)
        .attr("d", line)
        .attr("transform", null);

    // Slide it to the left.
    d3.active(this)
        .attr("transform", "translate(" + x(0) + ",0)")
        .transition()
        .on("start", tick);

    // Pop the old data point off the front.
    data.shift();
}
///======================================/日志概览模块=============================================
