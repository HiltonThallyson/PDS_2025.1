class ImageGenerationService:
    def __init__(self, model):
        self.model = model

    def generate_image(self, prompt):
        image = self.model.generate_image_base64(prompt)
        return image