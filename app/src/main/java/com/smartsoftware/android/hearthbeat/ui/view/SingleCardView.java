package com.smartsoftware.android.hearthbeat.ui.view;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.ActivityView;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 29.10.2015
 * Time: 21:42
 * Email: mrahbar.azad@gmail.com
 */
public class SingleCardView implements ActivityView {

    private static final int IN_ANIM_DURATION = 350;
    private static final int OUT_ANIM_DURATION = 300;

    @Bind(R.id.single_card_imageview)
    ImageView cardImageView;

    @Bind(R.id.single_card_parentview)
    View parentView;

    @Bind(R.id.single_card_toolbar)
    Toolbar toolbar;

    @BindDimen(R.dimen.toolbar_height_material)
    int toolbarHeight;

    private SingleCardViewListener listener;
    private CollectionView.CardIntentBundle intentBundle;
    private Card card;
    private boolean firstLaunch;
    private int leftDelta, topDelta;
    private float widthScale, heightScale;
    private ColorDrawable parentViewBackground;
    private BitmapDrawable cardImageDrawable;
    private ColorMatrix colorizerMatrix = new ColorMatrix();

    public interface SingleCardViewListener {
        void bindViews(SingleCardView view);
        void onFinish();
        Resources getResources();
    }

    public SingleCardView(SingleCardViewListener listener, CollectionView.CardIntentBundle intentBundle, Card card, boolean firstLaunch) {
        this.listener = listener;
        this.intentBundle = intentBundle;
        this.card = card;
        this.firstLaunch = firstLaunch;
    }

    public void bind(BaseActivity activity) {
        activity.setContentView(getLayout());
        listener.bindViews(this);

        initializeViews();
        initializeToolbar();
        initializeFirstLaunch(firstLaunch);
    }

    public int getLayout() {
        return R.layout.activity_singlecard;
    }

    private void initializeViews() {
        parentViewBackground = new ColorDrawable(ContextCompat.getColor(parentView.getContext(),
                R.color.default_ltwhite_color));
        parentView.setBackground(parentViewBackground);

        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(card.getImg());
        cardImageDrawable = new BitmapDrawable(listener.getResources(), bitmap);
        cardImageView.setImageDrawable(cardImageDrawable);
    }

    private void initializeToolbar() {
        toolbar.setTranslationY(-toolbarHeight);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(v -> runExitAnimation());
    }

    private void initializeFirstLaunch(boolean firstLaunch) {
        // Only run the animation if we're coming from the parent activity, not if
        // we're recreated automatically by the window manager (e.g., device rotation)
        if (firstLaunch) {
            final int thumbnailTop = intentBundle.thumbnailTop;
            final int thumbnailLeft = intentBundle.thumbnailLeft;
            final int thumbnailWidth = intentBundle.thumbnailWidth;
            final int thumbnailHeight = intentBundle.thumbnailHeight;

            ViewTreeObserver observer = cardImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    cardImageView.getViewTreeObserver().removeOnPreDrawListener(this);

                    int[] screenLocation = new int[2];
                    cardImageView.getLocationOnScreen(screenLocation);
                    leftDelta = thumbnailLeft - screenLocation[0];
                    topDelta = thumbnailTop - screenLocation[1];

                    widthScale = (float) thumbnailWidth / cardImageView.getWidth();
                    heightScale = (float) thumbnailHeight / cardImageView.getHeight();

                    runEnterAnimation();
                    return true;
                }
            });
        }
    }

    public void finish() {
        runExitAnimation();
    }

    private void runEnterAnimation() {
        cardImageView.setPivotX(0);
        cardImageView.setPivotY(0);
        cardImageView.setScaleX(widthScale);
        cardImageView.setScaleY(heightScale);
        cardImageView.setTranslationX(leftDelta);
        cardImageView.setTranslationY(topDelta);

        cardImageView.animate()
                .setDuration(IN_ANIM_DURATION)
                .scaleX(1).scaleY(1)
                .translationX(0).translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(() -> toolbar.animate().translationYBy(toolbarHeight));

        // Fade in the black background
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(parentViewBackground, "alpha", 0, 255);
        bgAnim.setDuration(IN_ANIM_DURATION);
        bgAnim.start();

        // Animate a color filter to take the image from grayscale to full color.
        // This happens in parallel with the image scaling and moving into place.
        ObjectAnimator colorizer = ObjectAnimator.ofFloat(SingleCardView.this,
                "saturation", 0, 1);
        colorizer.setDuration(IN_ANIM_DURATION);
        colorizer.start();
    }

    /**
     * This is called by the colorizing animator. It sets a saturation factor that is then
     * passed onto a filter on the picture's drawable.
     * @param value
     */
    public void setSaturation(float value) {
        colorizerMatrix.setSaturation(value);
        ColorMatrixColorFilter colorizerFilter = new ColorMatrixColorFilter(colorizerMatrix);
        cardImageDrawable.setColorFilter(colorizerFilter);
    }

    private void runExitAnimation() {
        toolbar.animate()
                .translationYBy(-toolbarHeight)
                .withEndAction(() -> {
                    final int thumbnailTop = intentBundle.thumbnailTop;
                    final int thumbnailLeft = intentBundle.thumbnailLeft;

                    int[] screenLocation = new int[2];
                    cardImageView.getLocationOnScreen(screenLocation);

                    leftDelta = thumbnailLeft - screenLocation[0];
                    topDelta = thumbnailTop - screenLocation[1];

                    // Fade out background
                    ObjectAnimator bgAnim = ObjectAnimator.ofInt(parentViewBackground, "alpha", 0);
                    bgAnim.setDuration(OUT_ANIM_DURATION);
                    bgAnim.start();

                    // Animate a color filter to take the image back to grayscale,
                    // in parallel with the image scaling and moving into place.
                    ObjectAnimator colorizer = ObjectAnimator.ofFloat(SingleCardView.this,
                            "saturation", 1, 0);
                    colorizer.setDuration(OUT_ANIM_DURATION);
                    colorizer.start();

                    cardImageView.animate()
                            .setDuration(OUT_ANIM_DURATION)
                            .scaleX(widthScale).scaleY(heightScale)
                            .translationX(leftDelta).translationY(topDelta)
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .withEndAction(listener::onFinish);
                });

    }
}
