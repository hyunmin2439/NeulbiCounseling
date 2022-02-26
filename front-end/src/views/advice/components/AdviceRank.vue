<template>
  <div class="d-flex justify-content-center">
    <article class="leaderboard">
      <header>
        <h1 class="leaderboard__title">
          <span class="mb-3 leaderboard__title--top" style="font-family: Binggrae-Taom;">오늘의 상담가</span>
          <span class="leaderboard__title--bottom">Today's Consultant</span>
        </h1>
      </header>
      
      <main class="leaderboard__profiles">
        <article class="leaderboard__profile d-flex justify-content-around py-1" v-for="(adviceRank, index) in state.adviceRankList" :key="index"
        >
          <img v-if="index==0" src="@/assets/medal/medal-gold.png" alt="Mark Zuckerberg" class="leaderboard__picture">
          <img v-else-if="index==1" src="@/assets/medal/medal-silver.png" alt="Mark Zuckerberg" class="leaderboard__picture">
          <img v-else src="@/assets/medal/medal-bronze.png" alt="Mark Zuckerberg" class="leaderboard__picture">
          <span class="leaderboard__name">{{ adviceRank.nickname }}</span>
          <span class="leaderboard__value">{{ adviceRank.pointTot }}점</span>
        </article>
      </main>
    </article>
  </div>
  <br>
</template>

<script>
import { computed, reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "AdviceRank",
  setup() {
    const store = useStore();

    const state = reactive({
      adviceRankList: computed(() => store.getters["root/adviceRankList"]),
    });

    return { state };
  },
};
</script>

<style scoped lang="scss">
.leaderboard {
  max-width: 75%;
  width: 60%;
  border-radius: 30px;
  
  header {
    --start: 15%;
    
    height: 120px;
    background-image: repeating-radial-gradient(circle at var(--start), transparent 0%, transparent 10%, #c2d6f8, #c2d6f8), linear-gradient(to right, #c2d6f8, #c2d6f8);
    color: #fff;
    position: relative;
    border-radius: 30px 30px 0 0;
    overflow: hidden;
    display: flex;    
    justify-content: center;
    
    .leaderboard__title {
      position: absolute;
      z-index: 2;
      top: 50%;
      transform: translateY(-50%);
      text-transform: uppercase;
      margin: 0;
      
      span {
        display: block;
        
      }
      
      &--top {
        font-size: 24px;
        font-weight: 700;
        letter-spacing: 6.5px;
      }
      
      &--bottom {
        font-size: 13px;
        font-weight: 500;
        letter-spacing: 3.55px;
        opacity: .65;
        transform: translateY(-2px);
      }
    }
    
    .leaderboard__icon {
      fill: #fff;
      opacity: .35;
      width: 50px;
      position: absolute;
      top: 50%;
      left: var(--start);
      transform: translate(-50%, -50%);
    }
  }
  
  &__profiles {
    background-color: #fff;
    border-radius: 0 0 30px 30px;
    padding: 15px 15px 20px;
    display: grid;
    row-gap: 8px;
  }
  
  &__profile {
    display: grid;
    grid-template-columns: 1fr 3fr 1fr;
    align-items: center;
    height: 100%;
    overflow: hidden;
    border-radius: 10px;
    box-shadow: 0 5px 7px -1px rgba(51, 51, 51, 0.23);
    transition: transform .25s cubic-bezier(.7,.98,.86,.98), box-shadow .25s cubic-bezier(.7,.98,.86,.98);
    background-color: #fff;
  }
  
  &__picture {
    // max-width: 100%;
    width: 13%;
    border-radius: 50%;
  }
  
  &__name {
    color: #979cb0;
    font-weight: 600;
    font-size: 30px;
    padding-right: 30px;
  }
  
  &__value {
    color: #9d9cb0;
    font-weight: 700;
    font-size: 30px;
    text-align: right;
    
    & > span {
      opacity: .8;
      font-weight: 600;
      font-size: 13px;
      margin-left: 3px;
    }
  }
}

.leaderboard {
  box-shadow: 0 0 40px -10px rgba(0, 0, 0, .4);
}</style>
