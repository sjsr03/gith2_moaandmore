package report.model.dao;
import java.util.List;

public class LinearRegression {
	
	private List Xdata;  // X축 데이터
    private List YData;  // Y축 데이터
    private Float result1;			// X축 평균
    private Float result2;			// Y축 평균

    public LinearRegression (List xdata, List YData) {
        this.Xdata = xdata;
        this.YData = YData;
    }

    // 예측값 도출하는 메서드 
    public Float predictValue (Float inputValue) {
        Float X1 = (float)Xdata.get(0);
        Float Y1 = (float)YData.get(0);
        Float Xmean = (float)getXMean(Xdata);
        Float Ymean = (float)getYMean(YData);
        Float lineSlope = getLineSlope(Xmean, Ymean, X1, Y1) ;
//        System.out.println("lineSlope : " + lineSlope);
        Float YIntercept = getYIntercept(Xmean , Ymean , lineSlope);
//        System.out.println("YIntercept : " + YIntercept);
        Float prediction = (lineSlope * inputValue) + YIntercept;
//        System.out.println("prediction : " + prediction);
        return prediction;
    }
    // y = ax + b : a = 가중치 b = 편향 
    
    // 가중치(그래프 각도) 계산 메서드  : 최소 제곱법 이용 
    // a = (x-x평균)(y-y평균)의 합 / (x-x평균)의 합의 제곱  
    public Float getLineSlope (Float Xmean, Float Ymean, Float X1, Float Y1) {
        float num1 = X1 - Xmean;
        float num2 = Y1 - Ymean;
        float denom = (X1 - Xmean) * (X1 - Xmean);
        return (num1 * num2) / denom;
    }

    // 편향(별도로 더해지는 값) 계산 메서드 
    public float getYIntercept (Float Xmean, Float Ymean, Float lineSlope) {
        return Ymean - (lineSlope * Xmean);
    }

    
    public Float getXMean (List Xdata) {
        result1 = 0.0f ;
        for (Integer i = 0; i < Xdata.size(); i++) {
            result1 = result1 + (float)Xdata.get(i);
        }
        return result1;
    }

    public Float getYMean (List Ydata) {
        result2 = 0.0f ;
        for (Integer i = 0; i < Ydata.size(); i++) {
            result2 = result2 + (float)Ydata.get(i);
        }
        return result2;
    }


}
	
	

