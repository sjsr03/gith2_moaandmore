// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

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




//Bar Chart Example
var myBarChart = new Chart(ctx, {
	type: 'horizontalBar',
	data: {
	    labels: labelList,
	    datasets: [{
	      label: "현재 지출액",
	      backgroundColor: backgroundColor,
	      hoverBackgroundColor: hoverBackgroundColor,
	      borderColor: borderColor,
	      data: dataList,
	    }],
	  },
	options: {
		// Elements options apply to all of the options unless overridden in a dataset
		// In this case, we are setting the border of each horizontal bar to be 2px wide
		elements: {
			rectangle: {
				borderWidth: 2,
			}
		},
		responsive: true,
		legend: {
			 display: false,
		},
		title: {
			display: false,
		},
		tooltips: {
		      titleMarginBottom: 10,
		      titleFontColor: '#6e707e',
		      titleFontSize: 14,
		      backgroundColor: "rgb(255,255,255)",
		      bodyFontColor: "#858796",
		      borderColor: '#dddfeb',
		      borderWidth: 1,
		      xPadding: 15,
		      yPadding: 15,
		      displayColors: false,
		      caretPadding: 10,
		      callbacks: {
		    	  label : function(tooltipItem) {
		    		  var item = '현재까지 지출액 : ' + number_format(tooltipItem.xLabel) + '원';
		    		  return item;
		    	  }
//			        label: function(tooltipItem, chart) {
//				          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
//				          return datasetLabel + ' : ' + number_format(tooltipItem.yLabel) + "원";
//			        }
		      }
	    }, 
	    scales: {
	      xAxes: [{
	        ticks: {
	          min: 0,
	          max: max,
	          maxTicksLimit: 1,
	          padding: 10,
	          beginAtZero: true,

	          // Include a dollar sign in the ticks
	          callback: function(value, index, values) {
	            return number_format(value) + "원";
	          }
	        },
	        gridLines: {
	          color: "rgb(234, 236, 244)",
	          zeroLineColor: "rgb(234, 236, 244)",
	          drawBorder: false,
	          borderDash: [2],
	          zeroLineBorderDash: [2]
	        }
	      }],
	      yAxes: [{
	        gridLines: {
	          display: false,
	          drawBorder: false
	        },
	        ticks: {
	          maxTicksLimit: 1
	        },
	        maxBarThickness: 25,
	      }],
	    },
	}
});

