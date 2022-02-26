<template>
  <div style="text-align: left; margin-left: 10px; margin-top: 20px;">
    <h3>닉네임 변경</h3>
    <br>
    <h5 class="mb-3">현재 닉네임 : {{ state.userInfo.nickname }}</h5>
    <div class="d-flex justify-content-start">
      <div class="searchBox">
        <input class="searchInput" type="text" placeholder="변경할 닉네임을 적어주세요." v-model="state.nickname" @keyup="clickValidateNickname">
      </div>
      <button class="front__text-hover mx-3 d-flex justify-content-start" @click="clickgetNickname">중복 확인</button>
      <button v-if="state.profileNicknameBool == true" class="front__text-hover d-flex justify-content-start" @click="clickmodifyNickname">
        닉네임 변경
      </button>
      <button type="button" class="front__text-hover_red mx-3" data-bs-toggle="modal" data-bs-target="#exampleModal1">비밀번호 변경</button>
        <!-- modal -->
        <div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">Password를 변경하세요.</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body">
                <form>
                  <div class="mb-3">
                    <label for="message-password" class="col-form-label">현재 비밀번호:</label>
                    <input type="password" class="form-control" id="message-password" v-model="state.password">
                    <label for="message-newPassword" class="col-form-label">새로운 비밀번호:</label>
                    <input type="password" class="form-control" id="message-newPassword" v-model="state.newPassword" @keyup="clickValidatePassword">
                    <div v-if="state.passwordValid===false">
                      <p style="color: red;">{{ state.passwordMessage }}</p>
                    </div>
                    <label for="message-newPasswordConfirmation" class="col-form-label">새로운 비밀번호 확인:</label>
                    <input type="password" class="form-control" id="message-newPasswordConfirmation" v-model="state.newPasswordConfirmation">
                    <div v-if="state.newPassword != state.newPasswordConfirmation">
                      <p style="color: red;">비밀번호가 일치하지 않습니다.</p>
                    </div>
                  </div>
                </form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="clickmodifyPassword">비밀번호 변경</button>
              </div>
            </div>
          </div>
        </div>
        <button class="front__text-hover_red" @click="clickresignUser">회원탈퇴</button>
    </div>
    <div v-if="state.nicknameValid===false" class="ms-3 mt-2">
      <p style="color: red;">{{ state.nicknameMessage }}</p>
    </div>
    
    <br><br><br>
    <h3>프로필 이미지 및 마스크 변경</h3>
    <br>
    <!-- 내가 추가한 부분 -->
    <div class="d-flex">
      <div style="margin-right: 10%;">
        <div id="imgFileUploadInsertThumbnail" class="thumbnail-wrapper mb-5">
          <!-- vue way img 를 만들어서 append 하지 않고, v-for 로 처리 -->
          <img class="card__thumb" v-bind:src="profile.profileImgThumbnail">
        </div>
        <div class="d-flex justify-content-center">
          <label class="front__text-hover" for="input-file" style="cursor: pointer;">업로드</label>
          <input @change="changeImgFile" type="file" id="input-file" style="display: none;"><!-- C -->
        </div>
      </div>
      <br><br><br>
      <br>
      <div class="ms-5">
        <div id="mask_id" class="mask-wrapper mb-4">
          <!-- vue way img 를 만들어서 append 하지 않고, v-for 로 처리 -->
          <img class="card__back" :src="mask.backUrl">
          <img class="card__mask" :src="mask.maskUrl">
        </div>
        <br>
        <div class="d-flex justify-content-start ms-5">
          <button class="front__text-hover d-flex justify-content-start me-4"  data-bs-toggle="modal" data-bs-target="#exampleModal">마스크 선택하기</button>
          <!-- 마스크 모달 -->
          <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel">마스크를 선택하세요.</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <form>
                    <div class="modalmask mb-3 row d-flex">
                      <img src="@/assets/mask/mask1.png" @click="clickSelectMask(1, $event)" class="col-4" id="mask-1">
                      <img src="@/assets/mask/mask2.png" @click="clickSelectMask(2, $event)" class="col-4" id="mask-2">
                      <img src="@/assets/mask/mask3.png" @click="clickSelectMask(3, $event)" class="col-4" id="mask-3">
                      <img src="@/assets/mask/mask4.png" @click="clickSelectMask(4, $event)" class="col-4" id="mask-4">
                      <img src="@/assets/mask/mask5.png" @click="clickSelectMask(5, $event)" class="col-4" id="mask-5">
                      <img src="@/assets/mask/mask6.png" @click="clickSelectMask(6, $event)" class="col-4" id="mask-6">
                      <img src="@/assets/mask/mask7.png" @click="clickSelectMask(7, $event)" class="col-4" id="mask-7">
                      <img src="@/assets/mask/mask8.png" @click="clickSelectMask(8, $event)" class="col-4" id="mask-8">
                      <img src="@/assets/mask/mask9.png" @click="clickSelectMask(9, $event)" class="col-4" id="mask-9">
                    </div>
                  </form>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
                  <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="clickmodifyMask">변경</button>
                </div>
              </div>
            </div>
          </div>
          <button class="front__text-hover d-flex justify-content-start ms-3"  data-bs-toggle="modal" data-bs-target="#exampleModal2">배경 선택하기</button>
          <!-- 마스크 모달 -->
          <div class="modal fade" id="exampleModal2" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="exampleModalLabel2">마스크를 선택하세요.</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                  <form>
                    <div class="modalback mb-3 row d-flex">
                      <img src="@/assets/back/back1.png" alt="" @click="clickSelectBack(1, $event)" class="col-6 mb-3" id="back-1">
                      <img src="@/assets/back/back2.png" alt="" @click="clickSelectBack(2, $event)" class="col-6 mb-3" id="back-2">
                      <img src="@/assets/back/back3.png" alt="" @click="clickSelectBack(3, $event)" class="col-6" id="back-3">
                      <img src="@/assets/back/back4.png" alt="" @click="clickSelectBack(4, $event)" class="col-6" id="back-4">
                    </div>
                  </form>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">종료</button>
                  <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="clickmodifyBack">변경</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <br><br><br>
  </div>
</template>

<script>
import { onMounted , reactive, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'ProfileUser',
  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      userInfo: computed(() => store.getters['root/userInfo']),
      nickname: null,
      nicknameMessage: '',
      nicknameValid: false,
      profileImg : null,
			profileNicknameBool: computed(() => store.getters['root/profileNicknameBool']),
      password: null,
      newPassword: null,
      newPasswordConfirmation: null,
      passwordMessage: "",
      passwordValid: false,
    })

    const profile = reactive({
      profileImgThumbnail : computed(() => `https://e202.s3.ap-northeast-2.amazonaws.com/${state.userInfo.profileImg}`),
    })

    const mask = reactive({
      maskId : null,
      backId: null,
      maskUrl: computed(() => require('@/assets/mask/mask'+state.userInfo.maskId+'.png')),
      backUrl: computed(() => require('@/assets/back/back'+state.userInfo.backId+'.png'))
    })

    const clickgetNickname = function () {
      store.dispatch('root/profileGetNickname', { nickname: state.nickname })
    }

    const clickValidateNickname = function () {
      let nicknameCheck = state.nickname
      let lengthNickname = nicknameCheck.length
      let specialNickname = nicknameCheck.search(
        /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi
      )
      let spaceNickname = nicknameCheck.search(/\s/)
      if (lengthNickname > 15) {
        state.nicknameMessage = "닉네임 길이 15자 미만"
        state.nicknameValid = false
      } else {
        if (specialNickname != -1 || spaceNickname != -1) {
          state.nicknameMessage =
            "닉네임에 특수문자나 공백을 포함 불가능"
          state.nicknameValid = false
        } else {
          state.nicknameMessage = "사용할 수 있는 닉네임입니다."
          state.nicknameValid = true
        }
      }
      if (lengthNickname == 0) {
        state.nicknameMessage = ""
        state.nicknameValid = false
      }
    }

    const clickValidatePassword = function () {
      let passwordCheck = state.newPassword
      let lengthPassword = passwordCheck.toString().length
      let engPassword = passwordCheck.search(/[a-z]/gi)
      let numPassword = passwordCheck.search(/[0-9]/g)
      let specialPassword = passwordCheck.search(
        /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi
      )
      if (lengthPassword < 8 || lengthPassword > 15) {
        state.passwordMessage = "8자 이상 15자 이하로 작성해주세요."
        state.passwordValid = false
      } else {
        if (engPassword == -1 || numPassword == -1 || specialPassword == -1) {
          state.passwordMessage =
            "영문, 숫자, 특수문자를 하나 이상 포함해야 합니다."
          state.passwordValid = false
        } else {
          state.passwordMessage = "사용할 수 있는 비밀번호입니다."
          state.passwordValid = true
        }
      }
      if (lengthPassword == 0) {
        state.passwordMessage = ""
        state.passwordValid = false
      }
    }

    const clickmodifyNickname = async function () {
      await store.dispatch('root/profileModifyNickname', { user_id: state.userInfo.id, nickname: state.nickname})
      await store.dispatch('root/userGetInfo', localStorage.getItem('jwt'))
      state.nickname == ""
    }

    const clickSelectMask = async function (num, event) {
      mask.maskId = num
      let targetId = event.currentTarget.id
      const topic_tag = document.getElementById(targetId)
      document.getElementById('mask-1').setAttribute('data-state', '')
      document.getElementById('mask-2').setAttribute('data-state', '')
      document.getElementById('mask-3').setAttribute('data-state', '')
      document.getElementById('mask-4').setAttribute('data-state', '')
      document.getElementById('mask-5').setAttribute('data-state', '')
      document.getElementById('mask-6').setAttribute('data-state', '')
      document.getElementById('mask-7').setAttribute('data-state', '')
      document.getElementById('mask-8').setAttribute('data-state', '')
      document.getElementById('mask-9').setAttribute('data-state', '')
      topic_tag.setAttribute('data-state', 'active')
    }

    const clickSelectBack = async function (num, event) {
      mask.backId = num
      let targetId = event.currentTarget.id
      const topic_tag = document.getElementById(targetId)
      document.getElementById('back-1').setAttribute('data-state', '')
      document.getElementById('back-2').setAttribute('data-state', '')
      document.getElementById('back-3').setAttribute('data-state', '')
      document.getElementById('back-4').setAttribute('data-state', '')
      topic_tag.setAttribute('data-state', 'active')
    }

    const clickmodifyMask = async function () {
      await store.dispatch('root/profileModifyMask', { user_id: state.userInfo.id, mask_id: mask.maskId})
      await store.dispatch('root/userGetInfo', localStorage.getItem('jwt'))
    }

    const clickmodifyBack = async function () {
      await store.dispatch('root/profileModifyBack', { user_id: state.userInfo.id, mask_id: mask.backId})
      await store.dispatch('root/userGetInfo', localStorage.getItem('jwt'))
    }

    ////////////////////
    const changeImgFile = async function (event) {
      if( event.target.files && event.target.files.length > 0 ) {
        const file = event.target.files[0];
        state.profileImg = file;
        profile.profileImgThumbnail = URL.createObjectURL(file); // 파일 경로로 바꿔서 추가
        await store.dispatch('root/profileModifyProfileImg', { user_id: state.userInfo.id, profile_img: state.profileImg })
        await store.dispatch('root/userGetInfo', localStorage.getItem('jwt'))
      }
    }

    const clickmodifyPassword = async function () {
      await store.dispatch('root/profileModifyPassword', { user_id: state.userInfo.id, password: state.newPassword})
      await store.dispatch('root/userGetInfo', localStorage.getItem('jwt'))
    }

    const clickresignUser = async function () {
      await store.dispatch('root/profileResignUser', { user_id: state.userInfo.id })
      await router.push({
        name: 'Home'
      })
      
    }

    return {
      onMounted, state, mask, profile,
      clickgetNickname, 
      clickValidateNickname,
      clickValidatePassword,
      clickSelectMask,
      clickSelectBack,
      clickmodifyNickname, 
      clickmodifyMask, 
      clickmodifyBack,
      changeImgFile, // 수정
      clickmodifyPassword, 
      clickresignUser
    }
  }
}
</script>

<style scoped lang="scss">

.modalmask img[data-state=active] {
  // border: 4px solid green;
  background: grey;
}

.modalback img[data-state=active] {
  border: 10px solid grey;
  background: grey;
}

.searchBox {
  position: relative;
  // transform:  translate(-50%,50%);
  background: white;
  height: 100%;
  width: 30%;
  // right: 10%;
  border-radius: 40px;
  padding: 10px;
  border: 2px solid #bbd2f9;
}


.searchInput {
  border:none;
  background: none;
  outline:none;
  float:left;
  padding: 0;
  color: black;
  font-size: 16px;
  transition: 0.4s;
  line-height: 20px;
  width: 100%;
  padding: 0 6px;
}


@media screen and (max-width: 620px) {
.searchBox:hover > .searchInput {
    width: 150px;
    padding: 0 6px;
  }
}

.front__text-hover {
  position: relative;
  // top: 10px;
  font-size: 15px;
  color: #bbd2f9;
  backface-visibility: hidden;

  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: .4px;

  border: 2px solid #bbd2f9;
  padding: 8px 15px;
  margin-top: 0px;
  border-radius: 30px;

  background: #bbd2f9;
  color: #fff;
}

.front__text-hover_red {
  position: relative;
  // top: 10px;
  font-size: 15px;
  backface-visibility: hidden;

  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: .4px;

  border: 2px solid #f77373;
  padding: 8px 15px;
  margin-top: 0px;
  border-radius: 30px;

  background: #f77373;
  color: #fff;
}

.thumbnail-wrapper{
  width: 250px;
  height: 250px;
}

.card__thumb {
  flex-shrink: 0;
  width: 100%;
  height: 100%;      
  border-radius: 50%;      
}

.mask-wrapper{
  // width: 100%;
  height: 250px;
}

.card__back {
  flex-shrink: 0;
  // width: 100%;
  height: 100%;      
  border: 1px solid grey;
  // border-radius: 50%;      
}

.card__mask {
  position: relative;
  flex-shrink: 0;
  height: 100%;
  left: -280px;
  // border-radius: 50%;      
}
</style>