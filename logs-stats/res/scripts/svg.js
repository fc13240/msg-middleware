/**
 * 生成普通日志概览曲线
 */
function createHourlyLogsCurve() {
    var n = 60;
    var random = d3.randomNormal(1000, 200);
    var data = d3.range(0, n, 1).map(random);

    var svg = d3.select("#svgLogsGenaral"),
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
        .attr("class", "logs_genereal_line")
        .transition()
        .duration(1000)
        .ease(d3.easeLinear)
        .on("start", function tick() {

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
        });
}

/**
 * 生成异常日志变化曲线
 */
function createExpLogsCurve() {
    var n = 26;
    var random = d3.randomNormal(50, 15);
    var data = d3.range(0, n, 1).map(random);

    var svg = d3.select("#svgLogsExp"),
        margin = {top: 20, right: 20, bottom: 40, left: 100},
        width = svg.attr("width") - margin.left - margin.right,
        height = svg.attr("height") - margin.top - margin.bottom,
        g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

    var x = d3.scaleLinear()
        .domain([1, n - 2])
        .range([0, width]);

    var y = d3.scaleLinear()
        .domain([0, 100])
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
        .attr("transform", "translate(0," + y(0) + ")")
        .call(d3.axisBottom(x));

    g.append("g")
        .attr("class", "axis axis--y")
        .attr("transform", "translate(" + x(0) + ",0)")
        .call(d3.axisLeft(y));

    g.append("g")
        .attr("clip-path", "url(#clip)")
        .append("path")
        .datum(data)
        .attr("class", "logs_exp_line")
        .transition()
        .duration(1000)
        .ease(d3.easeLinear)
        .on("start", function tick() {

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
        });
}

/**
 * 生成异常分布统计环形图
 */
function createExpStatsPie() {

    var svg = d3.select("#svgExpStatsPie");
    var w = 220;
    var h = 220;

    var dataset = [ 5, 10, 20, 45, 6, 25 ];

    var outerRadius = w / 2;
    var innerRadius = w / 3;
    var arc = d3.arc()
        .innerRadius(innerRadius)
        .outerRadius(outerRadius);

    var pie = d3.pie();

    //Easy colors accessible via a 10-step ordinal scale
    var color = d3.scaleOrdinal(d3.schemeCategory20);

    //Create SVG element
    // var svg = d3.select("body")
    //     .append("svg")
    //     .attr("width", w)
    //     .attr("height", h);

    //Set up groups
    var arcs = svg.selectAll("g.arc")
        .data(pie(dataset))
        .enter()
        .append("g")
        .attr("class", "arc")
        .attr("transform", "translate(" + outerRadius + "," + outerRadius + ")");

    //Draw arc paths
    arcs.append("path")
        .attr("fill", function(d, i) {
            return color(i);
        })
        .attr("d", arc);

    //Labels
    arcs.append("text")
        .attr("transform", function(d) {
            return "translate(" + arc.centroid(d) + ")";
        })
        .attr("text-anchor", "middle")
        .text(function(d) {
            return d.value;
        });
}

/**
 * 生成异常统计环形图图例
 */
function createExpStatsLegend() {

    var legend = d3.select("#svgExpStatsLegend");
    legend.append("rect")
        .attr("width", 18)
        .attr("height", 18)
        .style("fill", orange);
}