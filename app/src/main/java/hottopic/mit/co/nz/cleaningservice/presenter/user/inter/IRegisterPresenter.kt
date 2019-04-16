package hottopic.mit.co.nz.cleaningservice.presenter.user.inter

import hottopic.mit.co.nz.cleaningservice.entities.users.UserInfo

interface IRegisterPresenter {
    fun register(userInfo: UserInfo)
}