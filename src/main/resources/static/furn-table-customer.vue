<template>
  <div class="container">
    <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">If you need furniture, you can choose from the
      following to rent!</p>
    <div class="card mb-5">
      <div class="card-header h5 mb-4">Furniture List</div>
      <table class="table table-striped table-hover">
        <thead>
          <th>ID</th>
          <th>Name</th>
          <th>Amount</th>
          <th>Available</th>
          <th>Image</th>
          <th>Select Amount & Lease</th>
        </thead>
        <tbody>
          <tr v-for="furn in computedFurns" :key="furn.id">
            <td>{{ furn.id }}</td>
            <td>{{ furn.name }}</td>
            <td>{{ furn.amount }}</td>
            <td>{{ furn.amount - furn.leasedAmount }}</td>
            <td class="text-center">
              <img :src="furn.imgUrl || './empty.jpg'" height="100" />
            </td>
            <td>
              <div class="input-group mt-4">
                <input type="number" min="1" :max="furn.amount - furn.leasedAmount" class="form-control"
                  placeholder="Amount" v-model="furn.inputAmount" />
                <button class="btn btn-primary" type="button" @click="handleLease(furn.id, furn.inputAmount)"
                  :disabled="(furn.amount - furn.leasedAmount) <= 0">Lease</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["furns"],
  data() {
    return {
      amounts: []
    };
  },
  methods: {
    async handleLease(furnId, amount) {
      const res = await fetch(`/api/lease?furniture_id=${furnId}&amount=${amount}`, {
        method: "PUT",
      });
      if (res.ok) {
        this.$emit("furn-leased");
        this.$emit("notify", "Lease completed!", "success");
      } else {
        const json = await res.json();
        this.$emit("notify", `ERROR: ${json.error}`, "alert");
      }
    },
  },
  computed: {
    computedFurns() {
      return this.furns.map(furn => ({ ...furn, inputAmount: "1" }))
    }
  }
};

</script>
