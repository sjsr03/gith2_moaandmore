// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

Chart.plugins.register({
	  beforeDraw: function (chart) {
		  if (chart.config.options.elements.center) {
		      //Get ctx from string
		      var ctx = chart.chart.ctx;

		      //Get options from the center object in options
		      var centerConfig = chart.config.options.elements.center;
		      var fontSize = centerConfig.fontSize || '50';
		      var fontStyle = centerConfig.fontStyle || 'Arial';
		      var txt = centerConfig.text;
		      var color = centerConfig.color || '#000';
		      var sidePadding = centerConfig.sidePadding || 20;
		      var sidePaddingCalculated = (sidePadding/100) * (chart.innerRadius * 2)
		      //Start with a base font of 30px
		      ctx.font = fontSize + "px " + fontStyle;

		      //Get the width of the string and also the width of the element minus 10 to give it 5px side padding
		      var stringWidth = ctx.measureText(txt).width;
		      var elementWidth = (chart.innerRadius * 2) - sidePaddingCalculated;

		      // Find out how much the font can grow in width.
		      var widthRatio = elementWidth / stringWidth;
		      var newFontSize = Math.floor(30 * widthRatio);
		      var elementHeight = (chart.innerRadius * 0.7);

		      // Pick a new font size so it will not be larger than the height of label.
		      var fontSizeToUse = Math.min(newFontSize, elementHeight);

		      //Set font settings to draw it correctly.
		      ctx.textAlign = 'center';
		      ctx.textBaseline = 'middle';
		      var centerX = ((chart.chartArea.left + chart.chartArea.right) / 2);
		      var centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 2);

		      //반도넛일 경우
		      if (chart.config.options.rotation === Math.PI && chart.config.options.circumference === Math.PI) {
		        centerY = ((chart.chartArea.top + chart.chartArea.bottom) / 1.3);
		      }
		      ctx.font = fontSizeToUse+"px " + fontStyle;
		      ctx.fillStyle = color;

		      //Draw text in center
		      ctx.fillText(txt, centerX, centerY);
		    }
	  }
	});

function number_format(number, decimals, dec_point, thousands_sep) {
	  // *     example: number_format(1234.56, 2, ',', ' ');
	  // *     return: '1 234,56'
	  number = (number + '').replace(',', '').replace(' ', '');
	  var n = !isFinite(+number) ? 0 : +number,
	    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
	    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
	    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
	    s = '',
	    toFixedFix = function(n, prec) {
	      var k = Math.pow(10, prec);
	      return '' + Math.round(n * k) / k;
	    };
	  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
	  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	  if (s[0].length > 3) {
	    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
	  }
	  if ((s[1] || '').length < prec) {
	    s[1] = s[1] || '';
	    s[1] += new Array(prec - s[1].length + 1).join('0');
	  }
	  return s.join(dec);
	}



// Pie Chart Example
var myPieChart = new Chart(ctx, {
  type: 'doughnut',
  data: {
    labels: ["달성액", "남은 금액"],
    datasets: [{
      data: dataSet,
      backgroundColor: ['#0071a1', '#e6e6e6'],
      hoverBackgroundColor: ['#2e9cdb', '#e6e6e6'],
      hoverBorderColor: "rgba(234, 236, 244, 1)",
    }],
  },
  options: {
    maintainAspectRatio: false,
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      caretPadding: 10,
    },
    legend: {
      display: false
    },
    cutoutPercentage: 80,
    elements:{
    	center:{
    		text: ratio.toFixed() + "% 달성", 
    		color:"#0071a1",
    		sidePadding: 15 //Default 20 (as a percentage)
    	},
    },
  },
});




