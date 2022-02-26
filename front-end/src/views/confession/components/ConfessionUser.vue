<template>
  <div class="mb-4 d-flex justify-content-center">
    <div class="outer-div">
      <div class="inner-div">
        <div class="front">
          <div class="front__bkg-photo">
            <img :src="profile.profileImgThumbnail" style="width: 100%;">
          </div>
          <div class="front__face-photo">
            <img :src="require('@/assets/mask/mask'+state.userInfo.maskId+'.png')" style="position: relative; width: 100%; z-index: 71;">
            <img :src="require('@/assets/back/back'+state.userInfo.backId+'.png')" style="position: relative; width: 150%; top: -120%; z-index: 70;">
          </div>
          <div class="front__text" style="font-family: Binggrae;">
            <h3 class="front__text-header">{{ state.userInfo.nickname }}</h3>
            <p>{{ state.userInfo.email }}<br>
            포인트: {{ state.userInfo.pointTot }}</p>
                    
            <button @click="goToAdvice" class="front__text-hover mb-4">고민상담 페이지</button><br>
            <button class="front__text-hover" @click="goToProfile">내 프로필 페이지</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useRouter } from 'vue-router'
export default {
  name: "ConfessionUser",
  props: {
    userInfo: Object,
  },
  setup(props) {
    const router = useRouter()
    const state = reactive({
      userInfo: props.userInfo,
      profileImgThumbnail : `/profile/image/${props.userInfo.id}`,
      grade: 'None',
    });

    const profile = reactive({
      profileImgThumbnail : computed(() => `https://e202.s3.ap-northeast-2.amazonaws.com/${state.userInfo.profileImg}`),
    })

    const goToAdvice = function () {
      router.push({ name: 'Advice' })
    }

    const goToProfile = function () {
      router.push({
        name: 'Profile',
        params: {
          user_id: state.userInfo.id
        },
      })
    }

    return { state, profile, goToAdvice, goToProfile };
  },
};
</script>

<style scoped lang="scss">

.outer-div,
.inner-div {
  height: 70%;
  max-width: 75%;
  margin: 0 auto;
  position: relative;
}

.outer-div {
  perspective: 900px;
  perspective-origin: 50% calc(50% - 18em);
}

.inner-div {
  margin: 0 auto;
  border-radius: 5px;
  font-weight: 400;
  color: #071011;
  font-size: 1rem;
  text-align: center;
  transition: all 0.6s cubic-bezier(0.8, -0.4, 0.2, 1.7);
  transform-style: preserve-3d;

  
  &:hover .social-icon {
    opacity: 1;
    top: 0;
  }

  /*&:hover .front__face-photo,
  &:hover .front__footer {
    opacity: 1;
  }*/
}


.front {
  position: relative;
  top: 0;
  left: 0;
  backface-visibility: hidden;
}

.front {
  height: 100%;
  background: #fff;
  backface-visibility: hidden;
  border-radius: calc(var(--curve) * 1px);
  --surface-color: #fff;
  --curve: 40;
  box-shadow: 0 15px 10px -10px rgba(0, 0, 0, 0.5), 0 1px 4px rgba(0, 0, 0, 0.3),
    0 0 40px rgba(0, 0, 0, 0.1) inset;
}

.front__bkg-photo {
  position: relative;
  height: 13vh;
  width: 100%;
  // background: url("https://images.unsplash.com/photo-1511207538754-e8555f2bc187?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=88672068827eaeeab540f584b883cc66&auto=format&fit=crop&w=1164&q=80")
  //   no-repeat;
  // background-size: cover;
  backface-visibility: hidden;
  overflow: hidden;
  border-top-left-radius: calc(var(--curve) * 1px);
  border-top-right-radius: calc(var(--curve) * 1px);
  --surface-color: #fff;
  --curve: 40;

  &:after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
  }
}

.front__face-photo {
  position: relative;
  top: -60px;
  height: 120px;
  width: 120px;
  margin: 0 auto;
  border-radius: 50%;
  border: 3px solid black;
  background: white;
  overflow: hidden;
 /* backface-visibility: hidden;
  transition: all 0.6s cubic-bezier(0.8, -0.4, 0.2, 1.7);
  z-index: 3;*/
}

.front__text {
  position: relative;
  top: -55px;
  margin: 0 auto;
  font-family: "Montserrat";
  font-size: 22px;
  backface-visibility: hidden;

  .front__text-header {
    font-weight: 700;
    font-family: "Oswald";
    text-transform: uppercase;
    font-size: 20px;
  }

  .front__text-para {
    position: relative;
    top: -5px;

    color: #000;
    font-size: 14px;
    letter-spacing: 0.4px;
    font-weight: 400;
    font-family: "Montserrat", sans-serif;
  }

  .front-icons {
    position: relative;
    top: 0;
    font-size: 14px;
    margin-right: 6px;
    color: gray;
  }

  .front__text-hover {
    position: relative;
    top: 10px;
    font-size: 15px;
    color: #bbd2f9;
    backface-visibility: hidden;
    font-family: "Binggrae-Taom";

    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: .4px;

    border: 2px solid #bbd2f9;
    padding: 8px 15px;
    border-radius: 30px;

    background: #bbd2f9;
    color: #fff;
  }
}

.social-media-wrapper {
  font-size: 36px;

  .social-icon {
    position: relative;
    top: 20px;
    margin-left: 5px;
    margin-right: 5px;
    opacity: 0;
    color: #fff;
    transition: all 0.4s cubic-bezier(0.3, 0.7, 0.1, 1.9);
  }

  .social-icon:nth-child(1) {
    transition-delay: 0.6s;
  }

  .social-icon:nth-child(2) {
    transition-delay: 0.7s;
  }

  .social-icon:nth-child(3) {
    transition-delay: 0.8s;
  }

  .social-icon:nth-child(4) {
    transition-delay: 0.9s;
  }
}

.fab {
  position: relative;
  top: 0;
  left: 0;
  transition: all 200ms ease-in-out;
}

.fab:hover {
  top: -5px;
}
</style>
