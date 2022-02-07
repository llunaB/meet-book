<template>
<div>
  <v-sheet>
    <v-slide-group show-arrows>
      <v-slide-item v-for="conference in conferences" :key="conference.id" class="col-1 ma-2">
        <conference-card :conference="conference">
        </conference-card>        
      </v-slide-item>
    </v-slide-group>
  </v-sheet>  
</div>
  
</template>
<script>
import ConferenceCard from './ConferenceCard'

export default {
  name: "ConferenceList",
  props: {
    conferences: Array,
  },
  components: {
    ConferenceCard
  },  
}
</script>