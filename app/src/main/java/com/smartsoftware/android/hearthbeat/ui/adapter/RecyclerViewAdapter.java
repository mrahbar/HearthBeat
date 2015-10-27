package com.smartsoftware.android.hearthbeat.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.smartsoftware.android.hearthbeat.R;
import com.smartsoftware.android.hearthbeat.model.Card;
import com.smartsoftware.android.hearthbeat.ui.widget.CardViewAware;
import com.smartsoftware.android.hearthbeat.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Mahmoud Reza Rahbar Azad
 * Date: 25.10.2014
 * Time: 15:54
 * Email: mrahbar.azad@gmail.com
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {

    private static final int CARD_TYPE = 1;

    public interface InteractionListener {
        void onCardClick(View cardView, Card card);
        void onCardLongClick(View v, Card card);
    }

    private Map<String, Collection<Card>> originalCard, displayedCards;
    private CardFilter filter;
    private InteractionListener listener;
    private Point size;

    public RecyclerViewAdapter(Map<String, Collection<Card>> cards, Point size) {
        this.size = size;
        originalCard = new HashMap<>(cards);
        displayedCards = new HashMap<>(cards);
    }

    public void setInteractionListener(InteractionListener interactionListener) {
        this.listener = interactionListener;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deckbuilder_element, parent, false);
        return new CardViewHolder(v, size);
    }

    @Override
    public void onBindViewHolder(CardViewHolder vH, int position) {
        bindCardView(vH, position);
    }

    private void bindCardView(CardViewHolder vh, int position) {
        Card card = getItem(position);
        ImageLoader.getInstance().
                displayImage(card.getImg(), vh.imageView, ImageLoaderUtils.CARD_IMAGE_NORMAL);
    }

    private Card getItem(int position) {
        return displayedCards.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return CARD_TYPE;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return displayedCards.size();
    }

    private Filter getFilter() {
        if (filter == null)
            filter = new CardFilter();

        return filter;
    }

    public void filter(String query) {
        getFilter().filter(query);
    }

    public void resetDisplaylist() {
        displayedCards = new ArrayList<>(originalCard);
        notifyDataSetChanged();
    }

    private class CardFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence query) {
            FilterResults results = new FilterResults();
            String queryString = query.toString();

//            ArrayList<Card> filteredNotes = new ArrayList<Card>(completeCards.size());
//
//            if (TextUtils.isEmpty(queryString)) {
//                results.values = filteredNotes;
//                results.count = filteredNotes.size();
//            } else {
//                for (Card note : completeCards) {
//                    if (queried(queryString, note))
//                        filteredNotes.add(note);
//                }
//
//                results.values = filteredNotes;
//                results.count = filteredNotes.size();
//            }

            return results;
        }

        private boolean queried(String queryString, Card note) {
            boolean queried = false;

            return queried;
        }

        private boolean containsQuery(String title, String query) {
            boolean filtered = false;
            String valueText = title.toLowerCase();

            if (!TextUtils.isEmpty(valueText)) {
                final String[] words = valueText.toLowerCase().split(" ");

                for (String word : words) {
                    if (word.contains(query.toLowerCase())) {
                        filtered = true;
                        break;
                    }
                }
            }

            return filtered;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            displayedCards = (ArrayList<Card>) results.values;

            if (displayedCards == null)
                resetDisplaylist();
            else if (results.count > 0)
                notifyDataSetChanged();
        }
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private Card card;
        private Point size;

        public final CardViewAware imageView;

        public CardViewHolder(View view, Point size) {
            super(view);
            this.size = size;
            imageView = new CardViewAware((ImageView) view.findViewById(R.id.card_image), size.x, size.y);
        }

        public String getId() {
            return card.getCardId();
        }
    }
}
