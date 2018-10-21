package hottopic.mit.co.nz.cleaningservice.presenter.home;

import android.content.Context;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo;
import hottopic.mit.co.nz.cleaningservice.model.home.IUserInfoEdit;
import hottopic.mit.co.nz.cleaningservice.model.home.UserInfoEditModel;
import hottopic.mit.co.nz.cleaningservice.view.home.me.IMeEditView;

public class MeEditPresenterImpl implements IMeEditPresenter {
    private IMeEditView iMeEditView;
    private IUserInfoEdit iUserInfoEdit;
    private Context context;

    public MeEditPresenterImpl(IMeEditView iMeEditView, Context context) {
        this.iMeEditView = iMeEditView;
        this.context = context;
    }

    @Override
    public void editUserInfo(UserInfo userInfo) {
        iUserInfoEdit = new UserInfoEditModel(context);
        if (iUserInfoEdit.editUserInfo(userInfo)) {
            iMeEditView.editUserInfoResult(iUserInfoEdit.getUserInfo(), Constants.RESPONSE_CODE_SUCCESSFUL);
        }else{
            iMeEditView.editUserInfoResult(new UserInfo(), Constants.RESPONSE_CODE_FAIL);
        }
    }
}
