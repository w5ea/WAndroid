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
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class WScratchView extends View {
	public static interface ProgressListener{
		void onProgressChanged(float progress);
	}
	class ScratchMarker{
		private int matrixSize;//��������
		private int markedSize;//�Ѿ���ǵ�������
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
	private static final int ScratchWidth = 40;
	private ProgressListener progressListener;
	
	public WScratchView(Context context,AttributeSet attrs){
		super(context,attrs);
		
//		setBackgroundColor(Color.GREEN);
	}
	public void reset(){
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		drawDefaultBitmap(width, height);
		marker.reset();
	}
	public void drawBackgroundText(String text) {
		TextPaint paint = new TextPaint();
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Config.RGB_565);
		int textSize = 50;
		paint.setTextSize(textSize);
		paint.setColor(Color.BLACK);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		int tWidth = (int) paint.measureText(text);
		canvas.drawText(text, width/2-tWidth/2, height-textSize/2, paint);
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
			
			drawBackgroundText("лл�ݹ�");
		}
		
		mCanvas.drawPath(mPath, mPaint);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

		
	private void drawDefaultBitmap(int width,int height){
		bitmap = Bitmap.createBitmap(width, height,Config.ARGB_8888);
//		bitmap = 
//				BitmapFactory.decodeResource(getResources(), R.drawable.prize_result_rechargeable_card_50);
//				decodeMutableBitmapFromResourceId(getContext(), R.drawable.prize_result_rechargeable_card_50);
		mCanvas = new Canvas(bitmap);
		mCanvas.drawColor(Color.GRAY);
		
		int textSize = 50;
		int strokeWidth = 6;
		TextPaint paint = new TextPaint();
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		paint.setColor(Color.DKGRAY);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(strokeWidth);
		paint.setStrokeCap(Cap.ROUND);
		String text = "��һ��";
		int tWidth = (int) paint.measureText(text);
		int posX = width/2-tWidth/2 ;int posY = height-textSize/2-strokeWidth;
		mCanvas.drawText(text, posX, posY, paint);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(1);
		mCanvas.drawText(text, posX, posY, paint);
		
		
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
}
