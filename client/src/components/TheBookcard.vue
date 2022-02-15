<template>
  <v-card
    class="bookcard"
    outlined
    min-width="560px"
    max-width="560px"
    :to="{name: 'Bookinfo', params: {id: book.id}}"
    >
    <div class="d-flex flex-no-wrap justify-space-between">
      <div class="mx-1 my-auto">
        <v-img width="160px" :src="book.thumbnailUrl"></v-img>
      </div>
      <div>
        <v-card-title :title="book.name">{{ title_trim }}</v-card-title>
        <v-card-subtitle class="text-right">{{ book.author }}<br>{{ book.pubdate }}</v-card-subtitle>
        <v-divider />
        <v-card-text>
          <p>{{ contents_trim }}</p>
        </v-card-text>
        
      </div>
    </div>
  </v-card>

  <!-- <v-card
    class="bookcard"
    max-width="0"
    :to="{name: 'Bookinfo', params: {id: book.id}}"
      >
      <template>
        <div class="book">
          <div class="back"></div>
          <div class="page6">
            <v-card-text>
              <v-card-title :title="book.name">{{ title_trim }}</v-card-title>
              <v-card-subtitle >{{ book.author }}<br>{{ book.pubdate }}</v-card-subtitle>
              <p>{{ contents_trim }}</p>
            </v-card-text>
          </div>
          <div class="page5">
          </div>
          <div class="page4"></div>
          <div class="page3"></div>
          <div class="page2"></div>
          <div class="page1"></div>
          <div class="front"><v-img :src="book.thumbnailUrl" contain style="height:300px" /></div>
        </div>
      </template>

  </v-card> -->
</template>

<script>
// 변수 설정
const title_max_length = 20
const content_max_length = 120


export default {
  name: 'TheBookcard',

  props: {
    book: Object,
  },


  computed: {
    // title과 content에서 긴 내용을 잘라냅니다.
    title_trim: function () {
      return (this.book.name.length < title_max_length ? this.book.name : this.book.name.slice(0, title_max_length) + ' ...')
    },
    contents_trim: function () {
      return (this.book.contents.length < content_max_length ? this.book.contents : this.book.contents.slice(0, content_max_length) + ' ...')
    },
  },
}
</script>

<style>

/* .book {
  transform-style: preserve-3d;
  position: relative;
  width: 257.66px;
  height: 300px;
  margin: 1rem;
  cursor: pointer;
  backface-visibility: visible;
}

.front, .back, .page1, .page2, .page3, .page4, .page5, .page6 {
  transform-style: preserve-3d;
  position: absolute;
  width: 257.66px;
  height: 300px;
  transform-origin: right center;
  transition: transform .5s ease-in-out, box-shadow .35s ease-in-out;
}

.front, .page1, .page3, .page5 {
  border-bottom-right-radius: .5em;
  border-top-right-radius: .5em;
}

.back, .page2, .page4, .page6 {
  border-bottom-right-radius: .5em;
  border-top-right-radius: .5em;
}

.page1 { 
  background: #efefef;
}

.page2 {
  background: #efefef;
}

.page3 {
  background: #f5f5f5;
}

.page4 {
  background: #f5f5f5;
}

.page5 {
  background: #fafafa;
}

.page6 {
  background: #fdfdfd;
}

.book:hover .front {
  transform: rotateY(160deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page1 {
  transform: rotateY(150deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page2 {
  transform: rotateY(30deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page3 {
  transform: rotateY(140deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page4 {
  transform: rotateY(40deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page5 {
  transform: rotateY(130deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .page6 {
  transform: rotateY(45deg) scale(1.1);
  box-shadow: 0 1em 3em 0 rgba(0, 0, 0, .2);
}

.book:hover .back {
  transform: rotateY(20deg) scale(1.1);
} */
</style>