package net.onest.photographget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleHeadView extends View {
    private Paint paint;//画笔
    private Bitmap bitmap;//图片
    private BitmapShader bitmapShader;//图片着色器
    public CircleHeadView(Context context) {
        super(context);
    }

    public CircleHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.paint=new Paint();//初始化画笔工具
        this.bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);//获取头像图片
        this.bitmapShader=new BitmapShader(this.bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);//设置着色器为边缘填充
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix=new Matrix();//初始化矩阵运算类
        float scale=(float)getWidth()/this.bitmap.getWidth();//获取图片要缩放的比例
        matrix.setScale(scale,scale);//设置缩放
        this.bitmapShader.setLocalMatrix(matrix);//设置变换矩阵
        this.paint.setShader(this.bitmapShader);//画笔设置着色器
        float half=(float) getWidth()/2;//获取圆心位置
        canvas.drawCircle(half,half,half,this.paint);//画出头像
    }

    public CircleHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
