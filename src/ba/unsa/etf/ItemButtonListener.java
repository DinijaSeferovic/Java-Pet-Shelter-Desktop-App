package ba.unsa.etf;

import ba.unsa.etf.dal.PetDAO;
import ba.unsa.etf.dal.User;

public interface ItemButtonListener {

    public void onClickListener(PetDAO pet);
    public void onClickListener(User user);
}
