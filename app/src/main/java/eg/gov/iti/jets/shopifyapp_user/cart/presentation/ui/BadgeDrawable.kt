package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import eg.gov.iti.jets.shopifyapp_user.R


class BadgeDrawable(context: Context) : Drawable() {
    private val mBadgePaint: Paint
    private val mBadgePaint1: Paint
    private val mTextPaint: Paint
    private val mTxtRect: Rect = Rect()
    private var mCount = ""
    private var mWillDraw = false

    init {
        val mTextSize: Float = context.resources.getDimension(com.google.android.material.R.dimen.m3_searchbar_text_size)
        mBadgePaint = Paint()
        mBadgePaint.color = Color.RED
        mBadgePaint.isAntiAlias = true
        mBadgePaint.style = Paint.Style.FILL
        mBadgePaint1 = Paint()
        mBadgePaint1.color = ContextCompat.getColor(
            context.applicationContext,
            R.color.Transparent
        )
        mBadgePaint1.isAntiAlias = true
        mBadgePaint1.style = Paint.Style.STROKE
        mTextPaint = Paint()
        mTextPaint.color = Color.WHITE
        mTextPaint.typeface = Typeface.DEFAULT
        mTextPaint.textSize = mTextSize
        mTextPaint.isAntiAlias = true
        mTextPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        if (!mWillDraw) {
            return
        }
        val bounds: Rect = bounds
        val width: Float = (bounds.right - bounds.left +0f)/2
        val height: Float = (bounds.bottom - bounds.top+0f)/2

        // Position the badge in the top-right quadrant of the icon.

        /*Using Math.max rather than Math.min */
        val radius = width.coerceAtLeast(height) / 2 / 2
        val centerX = width*2 - radius - 10
        val centerY = radius*2 - 10
        if (mCount.length <= 2) {
            // Draw badge circle.
            canvas.drawCircle(centerX, centerY, (radius + 7.5f), mBadgePaint1)
            canvas.drawCircle(centerX, centerY, (radius + 5.5f), mBadgePaint)
        } else {
            canvas.drawCircle(centerX, centerY, (radius + 8.5f), mBadgePaint1)
            canvas.drawCircle(centerX, centerY, (radius + 6.5f), mBadgePaint)
            //	        	canvas.drawRoundRect(radius, radius, radius, radius, 10, 10, mBadgePaint);
        }
        // Draw badge count text inside the circle.
        mTextPaint.getTextBounds(mCount, 0, mCount.length, mTxtRect)
        val textHeight: Float = mTxtRect.bottom - mTxtRect.top+0f
        val textY = centerY + textHeight / 2f
        if (mCount.length > 2) canvas.drawText(
            "99+",
            centerX,
            textY,
            mTextPaint
        ) else canvas.drawText(mCount, centerX, textY, mTextPaint)
    }

    /*
    Sets the count (i.e notifications) to display.
     */
    fun setCount(count: String) {
        mCount = count

        // Only draw a badge if there are notifications.
        mWillDraw = !count.equals("0", ignoreCase = true)
        invalidateSelf()
    }

    override fun setAlpha(alpha: Int) {
        // do nothing
    }

    override fun setColorFilter(cf: ColorFilter?) {
        // do nothing
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("PixelFormat.UNKNOWN", "android.graphics.PixelFormat")
    )
    override  fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}