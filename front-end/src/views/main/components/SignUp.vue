<template>
  <div class="bg-img">
		<div class="content">
			<header>회원가입</header>
			<form action="#">
				<div class="field">
					<span class="fa fa-user"></span>
					<input type="text" class="pass-key-email" required placeholder="이메일" v-model="credentials.email" @keyup="clickValidateEmail">
          <span v-if="state.emailValid===true" class="show-email" @click="checkEmail">중복확인</span>
				</div>
        <div v-if="state.emailValid===false">
          <p style="color: red;">{{ state.emailMessage }}</p>
        </div>
        <div class="field space">
					<span class="fa fa-user"></span>
					<input type="text" class="pass-key-nickname" required placeholder="닉네임" v-model="credentials.nickname" @keyup="clickValidateNickname">
          <span v-if="state.nicknameValid===true" class="show-nickname" @click="checkNickname">중복확인</span>
				</div>
        <div v-if="state.nicknameValid===false">
          <p style="color: red;">{{ state.nicknameMessage }}</p>
        </div>
				<div class="field space">
					<span class="fa fa-lock"></span>
					<input type="password" class="pass-key" required placeholder="비밀번호" v-model="credentials.password" @keyup="clickValidatePassword">
					<span class="show" @click="clickShow">숫자보기</span>
				</div>
        <div v-if="state.passwordValid===false">
          <p style="color: red;">{{ state.passwordMessage }}</p>
        </div>
        <div class="field space">
					<span class="fa fa-lock"></span>
					<input type="password" class="pass-key-confirm" required placeholder="비밀번호 확인" v-model="credentials.passwordConfirmation">
					<span class="show-confirm" @click="clickShow1">숫자보기</span>
				</div>
        <div v-if="credentials.password != credentials.passwordConfirmation">
          <p style="color: red;">비밀번호가 일치하지 않습니다.</p>
        </div>
				<br>
				<div class="field">
					<input type="button" value="회원가입" @click="clickSignUp">
				</div>
			</form>
      <br>
			<br>
			<div class="signup">
					이미 회원이신가요?&nbsp;&nbsp;
					<router-link :to="{ name: 'SignIn' }">로그인으로 이동</router-link><br><br>
        <router-link :to="{ name: 'Home' }">뒤로가기</router-link>
			</div>
		</div>
	</div>
</template>

<script>
import { reactive, computed } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "SignUp",
  setup() {
    const store = useStore()
    const router = useRouter()

    const credentials = reactive({
      nickname: null,
      email: null,
      password: null,
      passwordConfirmation: null,
    });

    const state = reactive({
      emailBool: computed(() => store.getters["root/mainEmailBool"]),
      nicknameBool: computed(() => store.getters["root/mainNicknameBool"]),
      emailMessage: "",
      nicknameMessage: "",
      passwordMessage: "",
      emailValid: false,
      nicknameValid: false,
      passwordValid: false,
    });

    const clickShow = function () {
			const pass_field = document.querySelector('.pass-key')
			const showBtn = document.querySelector('.show')
			if(pass_field.type === 'password') {
				pass_field.type = "text"
				showBtn.textContent = "숨기기"
				showBtn.color = "#3498db"
			} else {
				pass_field.type = "password"
				showBtn.textContent = "숫자보기"
				showBtn.color = "#222"
			}
		}

    const clickShow1 = function () {
			const pass_field = document.querySelector('.pass-key-confirm')
			const showBtn = document.querySelector('.show-confirm')
			if(pass_field.type === 'password') {
				pass_field.type = "text"
				showBtn.textContent = "숨기기"
				showBtn.color = "#3498db"
			} else {
				pass_field.type = "password"
				showBtn.textContent = "숫자보기"
				showBtn.color = "#222"
			}
		}

		const clickSignUp = function () {
			if (state.emailValid & state.nicknameValid & state.passwordValid) {
        store.dispatch('root/mainSingUp', credentials)
        .then((res) => {
          store.commit("root/MAIN_EMAIL_BOOL", false)
          store.commit("root/MAIN_NICKNAME_BOOL", false)
          router.push({
            name: 'SignIn'
          })
        })
        .catch((err) => {
          console.log(err, '회원가입')
        });
      } else {
        alert('회원가입이 불가능합니다.')
      }
		}

    const checkEmail = async function () {
      await store.dispatch("root/mainUserInfoCheck", {
        key: "email",
        value: credentials.email,
      })
      const showBtn = document.querySelector('.show-email')
      if (state.emailBool===true) {
        alert('사용가능한 이메일입니다.')
        showBtn.textContent = ""
      } else {
        alert('이미 존재하는 이메일입니다.')
      }
    };

		const checkNickname = async function () {
			await store.dispatch('root/mainUserInfoCheck', {
        key: 'nickname',
        value: credentials.nickname
      })
      const showBtn = document.querySelector('.show-nickname')
      if (state.nicknameBool===true) {
        alert('사용가능한 닉네임입니다.')
        showBtn.textContent = ""
      } else {
        alert('이미 존재하는 닉네임입니다.')
      }
		}

    const clickValidateEmail = function () {
      let emailCheck = credentials.email
      let lengthEmail = emailCheck.length
      var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
      if (emailRule.test(emailCheck)) {
        state.emailMessage = "사용할 수 있는 이메일입니다."
        state.emailValid = true
      } else {
        state.emailMessage = "이메일 양식을 맞춰주세요."
        state.emailValid = false
      }
      if (lengthEmail==0) {
        state.emailMessage = ""
        state.emailValid = false
      }
    }

    const clickValidateNickname = function () {
      let nicknameCheck = credentials.nickname
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
      let passwordCheck = credentials.password
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

    return {
      state,
      credentials,
      clickShow,
      clickShow1,
      clickSignUp,
      checkEmail,
      checkNickname,
      clickValidateEmail,
      clickValidateNickname,
      clickValidatePassword,
    }
  }
}
</script>

<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css?family=Montserrat:400,500,600,700|Poppins:400,500&display=swap');
*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  user-select: none;
}
.bg-img{
  // background: url('bg.jpg');
  height: 100%;
  background-size: cover;
  background-position: center;
}
.bg-img:after{
  position: absolute;
  content: '';
  top: 0;
  left: 0;
  height: 100%;
  width: 100%;
  background: rgba(0,0,0,0.7);
}
.content{
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 999;
  text-align: center;
  padding: 60px 32px;
  width: 370px;
  transform: translate(-50%,-50%);
  background: #c2d6f8;
	border-radius: 30px;
  box-shadow: -1px 4px 28px 0px rgba(0,0,0,0.75);
}
.content header{
  color: white;
  font-size: 33px;
  font-weight: 600;
  margin: 0 0 35px 0;
  font-family: 'Montserrat',sans-serif;
}
.field{
  position: relative;
  height: 45px;
  width: 100%;
  display: flex;
  background: rgba(255,255,255,0.94);
  border-radius: 30px;
}
.field span{
  color: #222;
  width: 60px;
  line-height: 45px;
}
.field input{
  height: 100%;
  width: 100%;
  background: transparent;
  border: none;
  outline: none;
  color: #222;
  font-size: 16px;
  font-family: 'Poppins',sans-serif;
  border-radius: 30px;
}
.space{
  margin-top: 16px;
}
.show{
  position: absolute;
  right: 13px;
  font-size: 13px;
  font-weight: 700;
  color: #222;
  display: none;
  cursor: pointer;
  font-family: 'Montserrat',sans-serif;
}
.pass-key:valid ~ .show{
  display: block;
}
.pass{
  text-align: left;
  margin: 10px 0;
}
.pass a{
  color: white;
  text-decoration: none;
  font-family: 'Poppins',sans-serif;
}
.pass:hover a{
  text-decoration: underline;
}
.show-confirm{
  position: absolute;
  right: 13px;
  font-size: 13px;
  font-weight: 700;
  color: #222;
  display: none;
  cursor: pointer;
  font-family: 'Montserrat',sans-serif;
}
.pass-key-confirm:valid ~ .show-confirm{
  display: block;
}
.show-email{
  position: absolute;
  right: 13px;
  font-size: 13px;
  font-weight: 700;
  color: #222;
  display: none;
  cursor: pointer;
  font-family: 'Montserrat',sans-serif;
}
.pass-key-email:valid ~ .show-email{
  display: block;
}
.show-nickname{
  position: absolute;
  right: 13px;
  font-size: 13px;
  font-weight: 700;
  color: #222;
  display: none;
  cursor: pointer;
  font-family: 'Montserrat',sans-serif;
}
.pass-key-nickname:valid ~ .show-nickname{
  display: block;
}
.field input[type="button"]{
  background: #3498db;
  border: 1px solid #2691d9;
  color: white;
  font-size: 18px;
  letter-spacing: 1px;
  font-weight: 600;
  cursor: pointer;
  font-family: 'Montserrat',sans-serif;
}
.field input[type="button"]:hover{
  background: #2691d9;
}
.login{
  color: white;
  margin: 20px 0;
  font-family: 'Poppins',sans-serif;
}
.links{
  display: flex;
  cursor: pointer;
  color: white;
  margin: 0 0 20px 0;
}
.links img{
  width: 80%;
}
i span{
  margin-left: 8px;
  font-weight: 500;
  letter-spacing: 1px;
  font-size: 16px;
  font-family: 'Poppins',sans-serif;
}
.signup{
  font-size: 15px;
  color: white;
  font-family: 'Poppins',sans-serif;
}
.signup a{
  color: #3498db;
  text-decoration: none;
}
.signup a:hover{
  text-decoration: underline;
}
</style>
