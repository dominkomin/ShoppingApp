package dk.itu.mmad.shoppingList;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditShoppingListItemFragment extends Fragment
{
	static String DONE_BUTTON_TEXT = "doneButtonText";
	static String CURRENT_PRODUCT = "currentProduct";

	EditText productNameEdit;
	EditText productAmountEdit;
	Button doneButton;

	ProductsDAO productsDAO;
	Product currentProduct;

	private OnProductsChangedListener productChangedListener;

	public EditShoppingListItemFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			productChangedListener = (OnProductsChangedListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement OnProductsChangedListener");
		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		productChangedListener = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{

		final View view = inflater.inflate(R.layout.fragment_edit_shopping_list_item, container, false);

		productsDAO = new ProductsDAO(view.getContext());
		productsDAO.open();

		doneButton = (Button) view.findViewById(R.id.doneEdit);
		final Button plusButton = (Button) view.findViewById(R.id.plusEdit);
		final Button minusButton = (Button) view.findViewById(R.id.minusEdit);

		productNameEdit = (EditText) view.findViewById(R.id.itemNameEdit);
		productAmountEdit = (EditText) view.findViewById(R.id.itemAmountEdit);

		minusButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (checkProductAmount(view)) return;
				int currentAmount = Integer.parseInt(productAmountEdit.getText().toString());
				if (currentAmount > 1)
					productAmountEdit.setText(Integer.toString(--currentAmount));
			}
		});

		plusButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (checkProductAmount(view)) return;
				int currentAmount = Integer.parseInt(productAmountEdit.getText().toString());
				productAmountEdit.setText(Integer.toString(++currentAmount));
			}
		});

		doneButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (productNameEdit.getText().length() == 0 )
				{
					Toast.makeText(view.getContext(), R.string.emptyName, Toast.LENGTH_LONG).show();
					return;
				}
				if (checkProductAmount(view)) return;

				if (currentProduct == null)
					productsDAO.saveProduct(productNameEdit.getText().toString(), Integer.parseInt(productAmountEdit.getText().toString()));
				else
					productsDAO.updateProduct(new Product(productNameEdit.getText().toString(), Integer.parseInt(productAmountEdit.getText().toString()), currentProduct.getId()));


				productNameEdit.setText(null);
				productAmountEdit.setText(R.string.one);
				currentProduct = null;

				doneButton.setText(R.string.add);
				productNameEdit.setHint(R.string.newProduct);

				productChangedListener.onProductsChanged();
			}
		});

		return view;
	}

	private boolean checkProductAmount(View view)
	{
		if (productAmountEdit.getText().length() == 0 )
		{
			Toast.makeText(view.getContext(), R.string.emptyAmount, Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}

	public void setProduct(Product product)
	{
		currentProduct = product;
		productNameEdit.setText(product.getName());
		productAmountEdit.setText(Integer.toString(product.getAmount()));

		doneButton.setText(R.string.edit);
		productNameEdit.setHint(R.string.editProduct);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		productsDAO.close();
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		outState.putString(DONE_BUTTON_TEXT, doneButton.getText().toString());
		outState.putParcelable(CURRENT_PRODUCT, currentProduct);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			doneButton.setText(savedInstanceState.getString(DONE_BUTTON_TEXT));
			currentProduct = savedInstanceState.getParcelable(CURRENT_PRODUCT);
		}
	}

	public interface OnProductsChangedListener
	{
		public void onProductsChanged();
	}
}
