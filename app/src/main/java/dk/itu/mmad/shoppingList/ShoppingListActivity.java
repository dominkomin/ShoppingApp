package dk.itu.mmad.shoppingList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ShoppingListActivity extends ActionBarActivity implements ShoppingListFragment.OnShoppingListItemSelectedListener, EditShoppingListItemFragment.OnProductsChangedListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_list);

		ShoppingListFragment listFragment = (ShoppingListFragment) getFragmentManager().findFragmentById(R.id.listFragment);

		if (listFragment != null)
		{
			TextView totalCount = (TextView) findViewById(R.id.totalItemsCountText);
			totalCount.setText(Integer.toString(listFragment.getItemsCount()));
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_shopping_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void ShoppingListItemSelected(Product product)
	{
		EditShoppingListItemFragment editFragment = (EditShoppingListItemFragment) getFragmentManager().findFragmentById(R.id.editItemFragment);

		if (editFragment != null)
		{
			editFragment.setProduct(product);
		}
	}

	@Override
	public void onProductsChanged()
	{
		ShoppingListFragment listFragment = (ShoppingListFragment) getFragmentManager().findFragmentById(R.id.listFragment);

		if (listFragment != null)
		{
			listFragment.updateList();
			TextView totalCount = (TextView) findViewById(R.id.totalItemsCountText);
			totalCount.setText(Integer.toString(listFragment.getItemsCount()));
		}


	}
}
