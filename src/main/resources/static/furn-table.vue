<template>
  <div class="container">
    <div class="card mb-5">
      <div class="card-header h5">Furniture Management</div>
      <div class="card-body">
        <div class="row justify-content-around">
          <div class="col d-flex justify-content-center">
            <label class="m-2">Furniture Name</label>
            <input v-model="addFurn_name" />
          </div>
          <div class="col d-flex justify-content-center">
            <label class="m-2">Amount</label>
            <input v-model.number="addFurn_amount" />
          </div>
          <div>
            <div class="row m-3">
              <div class="col-6 d-flex justify-content-center">
                <input class="" type="file" ref="addFurn_imgFileInput" />
              </div>
              <div class="col-6 d-flex justify-content-center">
                <button type="button" class="btn btn-outline-primary" @click="handleAddFurn">Add Furniture</button>
              </div>
            </div>
            <hr class="mb-5">

            <table class="table table-striped table-hover">
              <thead>
                <th>ID</th>
                <th>Name</th>
                <th>Amount</th>
                <th>Available</th>
                <th>Image</th>
                <th></th>
              </thead>
              <tbody>
                <tr v-for="furn in furns" :key="furn.id">
                  <td>{{ furn.id }}</td>
                  <td>{{ furn.name }}</td>
                  <td>{{ furn.amount }}</td>
                  <td>{{ furn.amount - furn.leasedAmount }}</td>
                  <td><img width="100" height="100" v-bind:src="furn.imgUrl" /></td>
                  <td><button type="button" class="mt-4 btn btn-outline-danger"
                      @click="handleDelFurn(furn.id)">Del</button></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
module.exports = {
  props: ["furns"],
  data() {
    return {
      addFurn_name: null,
      addFurn_amount: null,
    };
  },
  methods: {
    async handleAddFurn() {
      const formData = new FormData();
      formData.append("img_file", this.$refs.addFurn_imgFileInput.files[0]);
      await fetch(
        `/api/furniture?name=${this.addFurn_name}&amount=${this.addFurn_amount}`,
        {
          method: "PUT",
          body: formData,
        }
      );
      this.$emit("furns-update");
    },

    async handleDelFurn(furnId) {
      await fetch(`/api/furniture/${furnId}`, { method: "DELETE" });
      this.$emit("furns-update");
    },
  },
};
</script>
