<template>
    <div>
<link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">
<div class="container">
      <div class="row">
        <!-- Main content -->
        <div class="col-lg-9 mb-3">
          <div class="row text-left mb-5">
            <div class="col-lg-6 mb-3 mb-sm-0">
              <div class="dropdown bootstrap-select form-control form-control-lg bg-white bg-op-9 text-sm w-lg-50" style="width: 100%;">
              <select class="form-control form-control-lg bg-white bg-op-9 text-sm w-lg-50" data-toggle="select" tabindex="-98">
                <option> Categories </option>
                <option> Learn </option>
                <option> Share </option>
                <option> Build </option>
              </select>
              </div>
            </div>
            <div class="col-lg-6 text-lg-right">
              <div class="dropdown bootstrap-select form-control form-control-lg bg-white bg-op-9 ml-auto text-sm w-lg-50" style="width: 100%;">
                  <select class="form-control form-control-lg bg-white bg-op-9 ml-auto text-sm w-lg-50" data-toggle="select" tabindex="-98">
                    <option> Filter by </option>
                    <option> Votes </option>
                    <option> Replys </option>
                    <option> Views </option>
                  </select>
              </div>
            </div>
          </div>
          <!-- End of post 1 -->
          <div class="card row-hover pos-relative py-3 px-3 mb-3 border-warning border-top-0 border-right-0 border-bottom-0 rounded-0">
            <div class="row align-items-center">
              <div class="col-md-8 mb-3 mb-sm-0">
                <h5>
                  <a href="#" class="text-primary">Drupal 8 quick starter guide</a>
                </h5>
                <p class="text-sm"><span class="op-6">Posted</span> <a class="text-black" href="#">20 minutes</a> <span class="op-6">ago by</span> <a class="text-black" href="#">KenyeW</a></p>
                <div class="text-sm op-5"> <a class="text-black mr-2" href="#">#C++</a> <a class="text-black mr-2" href="#">#AppStrap Theme</a> <a class="text-black mr-2" href="#">#Wordpress</a> </div>
              </div>
              <div class="col-md-4 op-7">
                <div class="row text-center op-7">
                  <div class="col px-1"> <i class="ion-connection-bars icon-1x"></i> <span class="d-block text-sm">141 Votes</span> </div>
                  <div class="col px-1"> <i class="ion-ios-chatboxes-outline icon-1x"></i> <span class="d-block text-sm">122 Replys</span> </div>
                  <div class="col px-1"> <i class="ion-ios-eye-outline icon-1x"></i> <span class="d-block text-sm">290 Views</span> </div>
                </div>
              </div>
            </div>
          </div>
          <!-- /End of post 1 -->
          
        </div>
    </div>
</div>
</template>

<script>
import { computed, reactive } from '@vue/runtime-core'
import { useStore } from "vuex";
import { useRouter } from 'vue-router'

export default {
  name: 'CommunityView',
  props: {
    userInfo: Object,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter()
    const state = reactive({
      userInfo: props.userInfo,
      communityList: computed(() => store.getters["root/communityList"]),
      communityLastPageNum: computed(() => store.getters["root/communityLastPageNum"]),
      categories: [
        { key: "description", value: "내용" },
        { key: "title", value: "제목" },
        { key: "nickname", value: "닉네임" },
      ],
      categoryselect: 'select',
      categorykey: '',
      searchInput: '',
      page: 1,
      topic: null,
      pageSearchTopic: 'main',
    })

    const clickCommunityList = async function () {
      state.pageSearchTopic = 'main'
      state.page = 1
      await store.dispatch('root/communityGetCommunityList')
    }

    const clickSearchList = function (category) {
      state.categoryselect = category.value
      state.categorykey = category.key
    }

    const clickSearchCommunity = async function () {
      state.pageSearchTopic = 'search'
      state.page = 1
      await store.dispatch('root/communityGetSearchList', {
        key: state.categorykey,
        value: state.searchInput,
      })
    }

    const clickSelectList = async function (idx) {
      state.pageSearchTopic = 'topic'
      state.page = 1
      state.topic = idx===0 ? 'like' : 'view'
      await store.dispatch('root/communityGetSelectList',{
        key: idx===0 ? 'like' : 'view'
      })
    }

    const checkPage = async function(event) {
      let targetId = event.currentTarget.id;
      if(targetId == "prev") {
          state.page -= 1;
          if(state.page < 1) state.page = 1;
      }
      else if(targetId == "next") {
          state.page += 1;   
      }
      if(state.pageSearchTopic === 'main') {
        await store.dispatch("root/communityPageSearch",{
          size: 8,
          page: state.page,
        })
      } else if (state.pageSearchTopic === 'topic') {
        await store.dispatch("root/communityTopicPageSearch",{
          topic: state.topic,
          size: 8,
          page: state.page,
        })
      } else if (state.pageSearchTopic === 'search') {
        await store.dispatch("root/communitySearchPageSearch",{
          key: state.categorykey,
          value: state.searchInput,
          size: 8,
          page: state.page,
        })
      }
    }

    const goToCommunityDetail = async function (community_id) {
      await store.dispatch("root/communityGetDetail", {
        user_id: state.userInfo.id, community_id: community_id
      })
      await router.push({
        name: 'CommunityDetail',
          params: {
            community_id: community_id
          }
      })
    }

    return { state,
    clickCommunityList,
    goToCommunityDetail,
    clickSelectList,
    clickSearchList,
    clickSearchCommunity,
    checkPage
    }
  }
}
</script>


<style scoped lang="scss">
body{
    margin-top:20px;
    background:#eee;
    color: #708090;
}
.icon-1x {
    font-size: 24px !important;
}
a{
    text-decoration:none;    
}
.text-primary, a.text-primary:focus, a.text-primary:hover {
    color: #00ADBB!important;
}
.text-black, .text-hover-black:hover {
    color: #000 !important;
}
.font-weight-bold {
    font-weight: 700 !important;
}
</style>