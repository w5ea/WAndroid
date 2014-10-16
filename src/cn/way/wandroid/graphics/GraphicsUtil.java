package cn.way.wandroid.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class GraphicsUtil {
	
	public static int getWithOfRect(Rect rect){
		return rect.right - rect.left;
	}
	public static float getWithOfRectF(RectF rect){
		return rect.right - rect.left;
	}
	
	public static Point getPointThatAroundTheRectByAngle(float anglef,float rectSize){
	    double x = 0,y = 0;
	    double radius = rectSize/2;
	    double M_PI = Math.PI;
	    double M_PI_2 = M_PI/2;
	    double angle = anglef/180*M_PI;
	    Point center = new Point();
	    if (angle>=M_PI*2.0) {
	        angle -= M_PI*2.0;
	    }
	    if (angle>=0&&angle<M_PI_2) {
	        x = radius + abs(sin(angle)*radius);
	        y = radius - abs(cos(angle)*radius);
	    }
	    if (angle>=M_PI_2&&angle<M_PI) {
	        angle-=M_PI_2;
	        x = radius + abs(cos(angle)*radius);
	        y = radius + abs(sin(angle)*radius);
	    }
	    if (angle>=M_PI&&angle<M_PI_2+M_PI) {
	        angle-=M_PI;
	        x = radius - abs(sin(angle)*radius);
	        y = radius + abs(cos(angle)*radius);
	    }
	    if (angle>=M_PI_2+M_PI&&angle<M_PI*2.0) {
	        angle-=M_PI_2+M_PI;
	        x = radius - abs(cos(angle)*radius);
	        y = radius - abs(sin(angle)*radius);
	    }
//	    Log.d("test",String.format("angle=%f  width=%f~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%f:%f",angle,rectSize,x,y));
	    center.x = (int) x;
	    center.y = (int) y;
	    return center;
	}
	private static double abs(double d){
		return Math.abs(d);
	}
	private static double sin(double d){
		return Math.sin(d);
	}
	private static double cos(double d){
		return Math.cos(d);
	}
	
}
