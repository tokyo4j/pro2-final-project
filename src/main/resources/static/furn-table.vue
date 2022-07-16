<template>
  <div class="container">
    <p class="fs-5 m-4 mt-5" style="font-family: Georgia, serif;">We can add and delete our rental furniture from this
      form.</p>
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
                <th>Delete</th>
                <th>Edit</th>
              </thead>
              <tbody>
                <tr v-for="furn in furns" :key="furn.id">
                  <td>{{ furn.id }}</td>
                  <td>{{ furn.name }}</td>
                  <td>{{ furn.amount }}</td>
                  <td>{{ furn.amount - furn.leasedAmount }}</td>
                  <td class="text-center">
                    <img height="100" :src="furn.imgUrl || './empty.jpg'" />
                  </td>
                  <td>
                    <button type="button" class="mt-4 btn btn-outline-danger"
                      @click="handleDelFurn(furn.id)">Del</button>
                  </td>
                  <td>
                    <button @click="handleEditButton(furn)" type="button" class="mt-4 btn btn-success">Edit</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div v-if="isModalOpen">
      <div class="modal" @click.self="isModalOpen = false" style="display: block;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Edit this furniture</h4>
              <button @click="isModalOpen = false" type="button" class="btn-close" data-bs-dismiss="modal"
                aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <form>
                <div class="mb-3">
                  <label for="recipient-name" class="col-form-label">New name:</label>
                  <input v-model="editingFurn.name" type="text" class="form-control" />
                </div>
                <div class="mb-3">
                  <label for="recipient-name" class="col-form-label">New amount:</label>
                  <input v-model.number="editingFurn.amount" type="text" class="form-control" />
                </div>
                <div class="mb-3">
                  <label for="recipient-name" class="col-form-label">New image:</label>
                  <input type="file" @change="updatePreview" ref="updatedImgFileInput" />
                </div>
              </form>
              <div class="text-center"><img :src="previewUrl" height="150" /></div>
            </div>
            <div class="modal-footer">
              <button @click="isModalOpen = false" type="button" class="btn btn-secondary"
                data-bs-dismiss="modal">Close</button>
              <button @click="handleEditSubmit" type="button" class="btn btn-primary">Submit</button>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-backdrop show"></div>
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
      isModalOpen: false,
      editingFurn: null,
      previewUrl: null,
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
      this.$emit("notify", "Furniture added!", "success");
    },

    async handleDelFurn(furnId) {
      const res = await fetch(`/api/furniture/${furnId}`, { method: "DELETE" });
      if (res.ok) {
        this.$emit("furns-update");
        this.$emit("notify", "Furniture deleted!", "success");
      } else {
        const json = await res.json();
        this.$emit("notify", `ERROR: ${json.error}`, "alert");
      }
    },

    handleEditButton(furn) {
      this.editingFurn = JSON.parse(JSON.stringify(furn))
      this.previewUrl = furn.imgUrl || "./empty.jpg";
      this.isModalOpen = true;
    },

    async handleEditSubmit() {
      const formData = new FormData();
      const file = this.$refs.updatedImgFileInput.files[0];
      if (file)
        formData.append("img_file", this.$refs.updatedImgFileInput.files[0]);
      await fetch(`/api/furniture/${this.editingFurn.id}?name=${this.editingFurn.name}&amount=${this.editingFurn.amount}`,
        {
          method: "PATCH",
          body: formData
        }
      );
      this.isModalOpen = false;
      this.$emit("furns-update");
      this.$emit("notify", "Furniture updated!", "success");
    },

    updatePreview() {
      const file = this.$refs.updatedImgFileInput.files[0];
      if (file)
        this.previewUrl = URL.createObjectURL(file);
      else
        this.previewUrl = this.editingFurn.imgUrl || "./empty.jpg";
    },
  },
};
</script>
