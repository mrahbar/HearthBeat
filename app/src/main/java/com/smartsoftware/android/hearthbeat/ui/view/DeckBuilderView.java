package com.smartsoftware.android.hearthbeat.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.main.BaseActivity;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.ActivityView;
import com.smartsoftware.android.hearthbeat.ui.ScreenContainer;
import com.smartsoftware.android.hearthbeat.ui.adapter.DeckBuilderPagerAdapter;
import com.smartsoftware.android.hearthbeat.ui.widget.CardViewAware;
import com.smartsoftware.android.hearthbeat.utils.ImageLoaderUtils;
import com.smartsoftware.android.hearthbeat.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.BindInt;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 21.10.2015
 * Time: 23:32
 * Email: mrahbar.azad@gmail.com
 */
public class DeckBuilderView implements ActivityView, DeckBuilderPagerAdapter.PageFactory, View.OnClickListener {

    @BindInt(R.integer.card_grid_columns)
    int cardGridColumns;

    @BindInt(R.integer.card_grid_rows)
    int cardGridRows;

    @Bind(R.id.deckbuilder_viewpager)
    ViewPager viewPager;

    @BindDimen(R.dimen.toolbar_height_material)
    int toolbarHeight;

    @Bind(R.id.deckbuilder_toolbar)
    Toolbar toolbar;

    private DeckBuilderViewListener listener;
    private ArrayList<Integer> pagePositionClassNameMap, classNamePagePositionMap;
    private Spinner classNameSpinner, cardSetSpinner;
    private boolean ignoreSpinnerItemSelected = false;
    private Map<String, Collection<Card>> originalCards, filteredCards;
    private int cardViewWidth, cardViewHeight;

    public interface DeckBuilderViewListener {
        void bindViews(DeckBuilderView view);
        void showCard(CardIntentBundle cardBundle);
        void onFinish();
        Resources getResources();
    }

    public DeckBuilderView(DeckBuilderViewListener listener, Map<String, Collection<Card>> cards) {
        this.listener = listener;
        this.originalCards = cards;
    }

    public void bind(BaseActivity activity) {
        filteredCards = new HashMap<>();

        activity.setContentView(getLayout());
        listener.bindViews(this);
        initializeToolbar(activity, originalCards);
        initializeViewPager(originalCards);
        initializeCardViewSize(activity);
    }

    private void initializeToolbar(BaseActivity activity, final Map<String, Collection<Card>> cards) {
        ArrayAdapter<CharSequence> classNamesAdapter = ArrayAdapter.createFromResource(toolbar.getContext(),
                R.array.class_names, R.layout.support_simple_spinner_dropdown_item);
        classNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classNameSpinner = new Spinner(activity);
        classNameSpinner.setAdapter(classNamesAdapter);
        classNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (ignoreSpinnerItemSelected) {
                    ignoreSpinnerItemSelected = false;
                    return;
                }

                int page = classNamePagePositionMap.get(position);
                viewPager.setCurrentItem(page, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> cardSetAdapter = ArrayAdapter.createFromResource(toolbar.getContext(),
                R.array.card_set, R.layout.support_simple_spinner_dropdown_item);
        cardSetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cardSetSpinner = new Spinner(activity);
        cardSetSpinner.setAdapter(cardSetAdapter);
        cardSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    initializeViewPager(originalCards);
                } else {
                    final String cardSet = listener.getResources().getStringArray(R.array.card_set)[position];
                    for (String key : cards.keySet()) {
                        Collection<Card> collection = cards.get(key);
                        List<Card> filtered = new ArrayList<>();
                        for (Card c : collection) {
                            if (TextUtils.equals(c.getCardSet(), cardSet)) {
                                filtered.add(c);
                            }
                        }
                        filteredCards.put(key, filtered);
                    }
                    initializeViewPager(filteredCards);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toolbar.addView(classNameSpinner);
        toolbar.addView(cardSetSpinner);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> listener.onFinish());
    }

    public int getLayout() {
        return R.layout.activity_deckbuilder;
    }

    private void initializeViewPager(Map<String, Collection<Card>> cards) {
        Collection<Collection<Card>> cardsList = new ArrayList<>();
        final String[] classNames = listener.getResources().getStringArray(R.array.class_names);
        for (String className : classNames) {
            List<Card> collection = new ArrayList<>(cards.get(className));
            Collections.sort(collection, (lhs, rhs) -> Utils.compare(lhs.getCost(), rhs.getCost()));
            cardsList.add(collection);
        }

        final int elementsPerPageCount = cardGridColumns * cardGridRows;
        DeckBuilderPagerAdapter adapter = new DeckBuilderPagerAdapter(this, elementsPerPageCount, cardsList);

        pagePositionClassNameMap = new ArrayList<>(adapter.getCount());
        classNamePagePositionMap = new ArrayList<>(cards.size());

        Observable.from(cardsList)
                .map(list -> Math.round(Math.ceil(((double) list.size()) / elementsPerPageCount)))
                .subscribe(new Action1<Long>() {
                    int classNameIndex = 0;
                    int sum = 0;

                    @Override
                    public void call(Long pageCount) {
                        int count = pageCount.intValue();
                        classNamePagePositionMap.add(sum);
                        sum += count;

                        for (int i = 0; i < count; i++) {
                            pagePositionClassNameMap.add(classNameIndex);
                        }
                        classNameIndex++;
                    }
                });

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                int index = pagePositionClassNameMap.get(position);
                ignoreSpinnerItemSelected = true;
                classNameSpinner.setSelection(index);
            }
        });
    }

    private void initializeCardViewSize(BaseActivity activity) {
        Point displaySize = Utils.getDisplaySize(activity);
        cardViewWidth = displaySize.x/cardGridColumns;
        cardViewHeight = Math.round(1.5f * cardViewWidth);
    }

    public void onResume() {
        toolbar.animate()
                .translationYBy(toolbarHeight);
    }

    @Override
    public View onPageCreated(Context context, Collection<Card> cards) {
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setOrientation(GridLayout.HORIZONTAL);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        gridLayout.setColumnCount(cardGridColumns);
        gridLayout.setLayoutParams(new ViewPager.LayoutParams());
//        gridLayout.setBackgroundColor(Color.BLUE);

        for (Card c : cards) {
            ImageView cardView = new ImageView(context);
//            cardView.setBackgroundColor(Color.MAGENTA);
            cardView.setTag(c);
            cardView.setOnClickListener(this);
            CardViewAware viewAware = new CardViewAware(cardView, cardViewWidth, cardViewHeight);
            ImageLoader.getInstance()
                    .displayImage(c.getImg(), viewAware, ImageLoaderUtils.CARD_IMAGE_NORMAL);

            gridLayout.addView(cardView);
        }

        return gridLayout;
    }

    @Override
    public void onClick(View v) {
        Card card = (Card) v.getTag();

        CardIntentBundle bundle = new CardIntentBundle();
        int[] screenLocation = new int[2];
        v.getLocationOnScreen(screenLocation);

        int topOffset = v.getPaddingTop()+Utils.convertDipsToPixels(listener.getResources(), 6);

        bundle.cardId = card.getCardId();
        bundle.thumbnailHeight = cardViewHeight;
        bundle.thumbnailWidth = v.getWidth();
        bundle.thumbnailLeft = screenLocation[0];
        bundle.thumbnailTop = screenLocation[1] - topOffset;

        toolbar.animate()
                .translationYBy(-toolbarHeight)
                .withEndAction(() -> listener.showCard(bundle));

    }

    public static class CardIntentBundle {
        private static final String TARGET_LEFT_TAG = "targetLeftTag";
        private static final String TARGET_TOP_TAG = "targetTopTag";
        private static final String TARGET_WIDTH_TAG = "targetWidthTag";
        private static final String TARGET_HEIGHT_TAG = "targetHeightTag";
        private static final String TARGET_CARDID_TAG = "targetCardIdTag";

        public String cardId;
        public int thumbnailTop, thumbnailLeft;
        public int thumbnailWidth, thumbnailHeight;

        public Bundle toBundle() {
            final Bundle bundle = new Bundle();
            bundle.putString(TARGET_CARDID_TAG, cardId);
            bundle.putInt(TARGET_HEIGHT_TAG, thumbnailHeight);
            bundle.putInt(TARGET_WIDTH_TAG, thumbnailWidth);
            bundle.putInt(TARGET_LEFT_TAG, thumbnailLeft);
            bundle.putInt(TARGET_TOP_TAG, thumbnailTop);

            return bundle;
        }

        public static CardIntentBundle fromBundle(Bundle bundle) {
            CardIntentBundle intentBundle = new CardIntentBundle();

            intentBundle.cardId = bundle.getString(TARGET_CARDID_TAG);
            intentBundle.thumbnailHeight = bundle.getInt(TARGET_HEIGHT_TAG);
            intentBundle.thumbnailWidth = bundle.getInt(TARGET_WIDTH_TAG);
            intentBundle.thumbnailLeft = bundle.getInt(TARGET_LEFT_TAG);
            intentBundle.thumbnailTop = bundle.getInt(TARGET_TOP_TAG);

            return intentBundle;
        }
    }
}

