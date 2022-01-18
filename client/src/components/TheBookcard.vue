<template>
  <v-card
    class="TheBookcard"
    max-width="400"
    elevation="2">

    <div class="d-flex flex-no-wrap justify-space-between">
      <div class="mx-1 my-auto">
        <v-img :src="book.thumbnail_url"></v-img>
      </div>
      <div>
        <v-card-title>{{ book.title }}</v-card-title>
        <v-card-subtitle class="text-right">{{ book.author }}<br>{{ book.pubdate }}</v-card-subtitle>
        <v-divider />
        <v-card-text>
          <p>{{ contents_trim }}</p>
        </v-card-text>
        
      </div>
    </div>

  </v-card>
</template>

<script>
// 변수 설정 공간
const content_max_length = 100


export default {
  name: 'TheBookcard',

  // props: {
  //   book: Object,
  // },

  data: function () {
    return {
      // 아래는 예시 데이터입니다. 실제로는 상위 컴포넌트에서 목록으로 받은 자료를 props로 넘겨받을 것입니다.
      book: {
        title: '주기율표',
        author: '프리모 레비 지음;이현경 옮김',
        contents: '아우슈비츠 생존 작가 프리모 레비 사후 20주기를 맞아 그의 대표작 두 권이 국내 최초로 번역되었다. 극단적인 개인의 경험에서 끌어낸 심오한 통찰을, 보편적이면서 아름답기까지 한 언어로 풀어낸 수기 와, 명상록과 회고록의 성격을 지닌 기발하고 독특한 구성의 책 이다.',
        publisher: '돌베개',
        pubdate: '2007',
        thumbnail_url: 'http://image.aladin.co.kr/product/87/23/cover/8971992654_1.jpg',
      },
    }
  },

  computed: {
    // content_max_length 이상의 내용을 잘라냅니다.
    contents_trim: function () {
      return (this.book.contents < content_max_length ? this.book.content : this.book.contents.slice(0, content_max_length) + '...')
    }
  },
}
</script>

<style>

</style>