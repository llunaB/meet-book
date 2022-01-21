<template>
  <v-carousel
    cycle
    hide-delimiter-background
  >
    <v-carousel-item      
      v-for="(item,i) in items"
      :key="i"
      :src="item.src"    
    >
    </v-carousel-item>
  </v-carousel>
</template>
<script>
export default {
  name: "Carousel",
  data(){
    return {
      items: [
        {
          src: 'https://stanfordpress.typepad.com/.a/6a00d8342f027653ef01b7c888864a970b-800wi'
        },
        {
          src: 'https://media.istockphoto.com/photos/enjoying-a-drink-at-book-club-picture-id1132784599?k=20&m=1132784599&s=612x612&w=0&h=OhNu8lyfy-xTW_jdrqGzi9SD12aujG-zSLMd6r-VTr8='
        },
        {
          src: 'https://storyplanet.ca/wp-content/uploads/2015/10/DSC_1039-738x492.jpg'
        },
        {
          src: 'https://fellow.app/wp-content/uploads/2021/12/book-club.jpg'
        }
      ]
    }
  }
}
</script>