package dk.itu.mmad.shoppingList;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShoppingListFragment extends ListFragment
{
	ProductsDAO productsDAO;
	Cursor products;
	SimpleCursorAdapter productsCursorAdapter;

	int itemsCount;

	private OnShoppingListItemSelectedListener itemSelectedListener;

	public ShoppingListFragment()
	{
	}

	public int getItemsCount()
	{
		return itemsCount;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

		productsDAO = new ProductsDAO(view.getContext());
		productsDAO.open();

		products = productsDAO.getProducts();

		productsCursorAdapter = new SimpleCursorAdapter(view.getContext(),
				R.layout.shopping_list_item, products,
				new String[]{"name", "amount"}, new int[]{R.id.shoppingListItemName, R.id.shoppingListItemAmount});
		setListAdapter(productsCursorAdapter);

		calculateTotalCount();

		return view;
	}

	private void calculateTotalCount()
	{
		itemsCount = 0;
		products.moveToFirst();
		while (!products.isAfterLast())
		{
			itemsCount += products.getInt(products.getColumnIndexOrThrow("amount"));
			products.moveToNext();
		}
	}

	public void updateList()
	{
		products = productsDAO.getProducts();
		productsCursorAdapter.swapCursor(products);
		calculateTotalCount();
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			itemSelectedListener = (OnShoppingListItemSelectedListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		itemSelectedListener = null;
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		if (null != itemSelectedListener)
		{
			Cursor cursor = (Cursor) l.getItemAtPosition(position);
			Product product = new Product(cursor.getString(cursor.getColumnIndexOrThrow("name")), cursor.getInt(cursor.getColumnIndexOrThrow("amount")), cursor.getInt(cursor.getColumnIndexOrThrow("_id")));

			itemSelectedListener.ShoppingListItemSelected(product);
		}
	}

	public interface OnShoppingListItemSelectedListener
	{
		public void ShoppingListItemSelected(Product product);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		productsDAO.close();
	}
}
