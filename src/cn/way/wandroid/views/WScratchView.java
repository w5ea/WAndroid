package cn.way.wandroid.views;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * 已知BUG，在不同分辨率下坐标计算问题
 * @author Wayne
 *
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN) public class WScratchView extends View {
	public static interface ProgressListener{
		void onProgressChanged(float progress);
	}
	class ScratchMarker{
		private int matrixSize;//总坐标数
		private int markedSize;//已经标记的坐标数
		private int rowSize,colSize;
		private boolean[][] matrix;
		public ScratchMarker(int rowSize,int colSize) {
			this.rowSize = rowSize;
			this.colSize = colSize;
			matrixSize = rowSize*colSize;
			matrix = new boolean[rowSize][colSize];
		}
		public void markCoordinate(int x,int y){
			if (x<0||y<0||x>rowSize-1||y>colSize-1) {
				return;
			}
			if (!isCoordinateMarked(x, y)) {
				matrix[x][y] = true;
				markedSize++;
			}
//			Log.d("MTAB", "X="+x+" Y="+y+" markedSize="+markedSize);
		}
		public boolean isCoordinateMarked(int x,int y){
			return matrix[x][y];
		}
		public void reset(){
			matrix = null;
			matrix = new boolean[rowSize][colSize];
			markedSize = 0;
		}
		public float getMarkedProgress(){
			return markedSize/(float)matrixSize;
		}
	}
	
	private Canvas mCanvas = null;
	private Path mPath = null;
	private Paint mPaint = null;
	private Bitmap bitmap = null;
	private ScratchMarker marker;
	private static int ScratchWidth = 40;
	private String resultText = "once again";
	private ProgressListener progressListener;
	private float density;
	public WScratchView(Context context,AttributeSet attrs){
		super(context,attrs);
		density = getResources().getDisplayMetrics().density;
//		setBackgroundColor(Color.GREEN);
		ScratchWidth = (int) (40 * density);
	}
	public void reset(){
		marker.reset();
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		drawDefaultBitmap(width, height);
		postInvalidate();
	}
	
	public void clear(){
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		bitmap = Bitmap.createBitmap(width, height,Config.ARGB_8888);
		mCanvas = new Canvas(bitmap);
		mCanvas.drawColor(Color.TRANSPARENT);
		postInvalidate();
	}
	public void drawBackgroundText() {
		String text = getResultText();
		TextPaint paint = new TextPaint();
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		int textSize = (int) (50*density);
		paint.setTextSize(textSize);
		paint.setColor(Color.BLACK);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length()-1, bounds);
		paint.setTextAlign(Align.CENTER);
//		int tWidth = (int) paint.measureText(text);
		int posX = width/2;//-tWidth/2 ;
		int posY = height/2+bounds.height()/2;//height -(height/2-(int)(bounds.height()/2*density));
		canvas.drawText(text, posX, posY, paint);
//		int tWidth = (int) paint.measureText(text);
//		canvas.drawText(text, width/2-tWidth/2, height/2, paint);
		setBackground(new BitmapDrawable(getResources(), bitmap));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (mPaint==null) {
			mPath = new Path();
			
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			
			marker = new ScratchMarker(width/ScratchWidth, height/ScratchWidth);
			
			drawDefaultBitmap(width,height);
				
			mPaint = new Paint();
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setStyle(Style.STROKE);
			mPaint.setStrokeWidth(ScratchWidth);
			mPaint.setStrokeCap(Cap.ROUND);
			mPaint.setStrokeJoin(Join.ROUND);
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			mPaint.setAlpha(0);
			
			drawBackgroundText();
		}
		
		mCanvas.drawPath(mPath, mPaint);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	private Bitmap coverBitmap;
	private void drawDefaultBitmap(int width,int height){
		bitmap = Bitmap.createBitmap(width, height,Config.ARGB_8888);
//		Bitmap mbitmap = 
//				BitmapFactory.decodeResource(getResources(), R.drawable.star_big_off);
//				decodeMutableBitmapFromResourceId(getContext(), R.drawable.);
		mCanvas = new Canvas(bitmap);
		mCanvas.drawColor(Color.GRAY);
		
		if (getCoverBitmap()!=null) {
			int posX = width/2-getCoverBitmap().getWidth()/2 ;
			int posY = height/2-getCoverBitmap().getHeight()/2;
			mCanvas.drawBitmap(getCoverBitmap(), posX, posY, new Paint());
		}else{
//			int textSize = 50;
//			int strokeWidth = 6;
			int textSize = (int) (50*density);
			int strokeWidth = (int) (6*density);
			TextPaint paint = new TextPaint();
			paint.setAntiAlias(true);
			paint.setTextSize(textSize);
			paint.setColor(Color.DKGRAY);
			paint.setStyle(Style.FILL_AND_STROKE);
			paint.setStrokeWidth(strokeWidth);
			paint.setStrokeCap(Cap.ROUND);
			paint.setTextAlign(Align.CENTER);
			String text = "刮开";
			Rect bounds = new Rect();
			paint.getTextBounds(text, 0, text.length()-1, bounds);
//			int tWidth = (int) paint.measureText(text);
			int posX = width/2;//-tWidth/2 ;
			int posY = height/2-strokeWidth+textSize/2;// -(height/2-(int)(bounds.height()/2*density-strokeWidth/2));//-textSize/2-strokeWidth/2;
			mCanvas.drawText(text, posX, posY, paint);
			paint.setColor(Color.RED);
			paint.setStrokeWidth(1);
			mCanvas.drawText(text, posX, posY, paint);
		}
		
//		BitmapFactory.Options opt = new BitmapFactory.Options();
//		opt.inMutable = true;
//		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.prize_result_rechargeable_card_50, opt);
//		mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
	}
	
	int x = 0;
	int y = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int currX = (int) event.getX();
		int currY = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			mPath.reset();
			x = currX;
			y = currY;
			mPath.moveTo(x, y);
		}
			break;
		case MotionEvent.ACTION_MOVE: {
			mPath.quadTo(x, y, currX, currY);
			x = currX;
			y = currY;
			marker.markCoordinate(currX/ScratchWidth, currY/ScratchWidth);
			postInvalidate();
			if (progressListener!=null) {
				progressListener.onProgressChanged(marker.getMarkedProgress());
			}
		}
			break;
		case MotionEvent.ACTION_UP:{
//			Log.d("MTAB", "Progress: "+marker.getMarkedProgress());
//			if (marker.getMarkedProgress()>=0.5f) {
//				reset();
//			}
		}
		case MotionEvent.ACTION_CANCEL: {
			mPath.reset();
		}
			break;
		}
		return true;
	}
	public float getProgress(){
		return marker==null?0:marker.getMarkedProgress();
	}
	public ProgressListener getProgressListener() {
		return progressListener;
	}
	public void setProgressListener(ProgressListener listener) {
		this.progressListener = listener;
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static Bitmap decodeMutableBitmapFromResourceId(final Context context, final int bitmapResId) {
	    final Options bitmapOptions = new Options();
	    if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB)
	        bitmapOptions.inMutable = true;
	    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapResId, bitmapOptions);
	    if (!bitmap.isMutable())
	        bitmap = convertToMutable(context, bitmap);
	    return bitmap;
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static Bitmap convertToMutable(final Context context, final Bitmap imgIn) {
	    final int width = imgIn.getWidth(), height = imgIn.getHeight();
	    final Config type = Config.ARGB_8888;//imgIn.getConfig();
	    File outputFile = null;
	    final File outputDir = context.getCacheDir();
	    try {
	        outputFile = File.createTempFile(Long.toString(System.currentTimeMillis()), null, outputDir);
	        outputFile.deleteOnExit();
	        final RandomAccessFile randomAccessFile = new RandomAccessFile(outputFile, "rw");
	        final FileChannel channel = randomAccessFile.getChannel();
	        final MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, imgIn.getRowBytes() * height);
	        imgIn.copyPixelsToBuffer(map);
	        imgIn.recycle();
	        final Bitmap result = Bitmap.createBitmap(width, height, type);
	        map.position(0);
	        result.copyPixelsFromBuffer(map);
	        channel.close();
	        randomAccessFile.close();
	        outputFile.delete();
	        return result;
	    } catch (final Exception e) {
	    } finally {
	        if (outputFile != null)
	            outputFile.delete();
	    }
	    return null;
	}
	public String getResultText() {
		return resultText;
	}
	public void setResultText(String resultText) {
		this.resultText = resultText;
	}
	public Bitmap getCoverBitmap() {
		return coverBitmap;
	}
	public void setCoverBitmap(Bitmap coverBitmap) {
		this.coverBitmap = coverBitmap;
	}
}
