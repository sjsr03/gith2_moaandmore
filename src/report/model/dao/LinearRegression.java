package report.model.dao;
import java.util.List;

public class LinearRegression {
	
	private List Xdata;  // X축 데이터
    private List YData;  // Y축 데이터
    private Double result1;			// X축 평균
    private Double result2;			// Y축 평균

    public LinearRegression (List xdata, List YData) {
        this.Xdata = xdata;
        this.YData = YData;
    }

    // 예측값 도출하는 메서드 
    public Double predictValue (long inputValue) {
    	Double X1 = (Double)Xdata.get(0);
    	Double Y1 = (Double)YData.get(0);
    	Double Xmean = (Double)getXMean(Xdata);
    	Double Ymean = (Double)getYMean(YData);
    	Double lineSlope = getLineSlope(Xmean, Ymean, X1, Y1) ;
    	Double YIntercept = getYIntercept(Xmean , Ymean , lineSlope);
    	Double prediction = (lineSlope * inputValue) + YIntercept;
        return prediction;
    }
    // y = ax + b : a = 가중치 b = 편향 
    
    // 가중치(그래프 각도) 계산 메서드  : 최소 제곱법 이용 
    // a = (x-x평균)(y-y평균)의 합 / (x-x평균)의 합의 제곱  
    public Double getLineSlope (Double Xmean, Double Ymean, Double X1, Double Y1) {
    	Double num1 = X1 - Xmean;
    	Double num2 = Y1 - Ymean;
    	Double denom = (X1 - Xmean) * (X1 - Xmean);
        return (num1 * num2) / denom;
    }

    // 편향(별도로 더해지는 값) 계산 메서드 
    public Double getYIntercept (Double Xmean, Double Ymean, Double lineSlope) {
        return Ymean - (lineSlope * Xmean);
    }

    
    public Double getXMean (List Xdata) {
        result1 = 0.0 ;
        for (Integer i = 0; i < Xdata.size(); i++) {
            result1 = result1 + (Double)Xdata.get(i);
        }
        return result1;
    }

    public Double getYMean (List Ydata) {
        result2 = 0.0 ;
        for (Integer i = 0; i < Ydata.size(); i++) {
            result2 = result2 + (Double)Ydata.get(i);
        }
        return result2;
    }


}
	
	

