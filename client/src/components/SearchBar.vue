<template>
    <!-- 검색 -->
    <div>
        <v-text-field
        dense flat
        placeholder="아몬드"
        hide-details="true"
        label="Prepend"
        prepend-inner-icon="mdi-magnify"
        style=""
        v-model="keyword"
        @keyup.enter="searchKeyword(keyword)"
        @click:append-outer="searchKeyword(keyword)" />
        
    </div>
</template>

<script>
export default {
    name: "SearchBar",
    data() {
        return{
            keyword: '',
        }
    },
    methods: {
        // 검색
        // Navbar에서의 검색은 실제 검색을 수행하는 것이 아니라,
        // 검색 페이지에 검색 키워드로 전달하는 방식으로 수행됩니다.
        searchKeyword: function (keyword) {
        console.log(keyword)
        this.$router.push({name: 'Search', params: {'type': 'conference'}, query: {'keyword': String(keyword), 'page': parseInt(1)}})
        },
    }
}
</script>

<style>

</style>