# Bean validation with ControlsFX

### example

With JavaFX bean

    public class Person {
        private StringProperty name = new SimpleStringProperty();
        private StringProperty address = new SimpleStringProperty();
        private StringProperty email = new SimpleStringProperty();

        public StringProperty nameProperty() { return name; }
        @NotNull(message = "이름은 필수 항목 입니다.")
        @Size(min = 1, max = 30, message = "이름의 길이는 1~30자 입니다.")
        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }

        public StringProperty addressProperty() { return address; }
        public String getAddress() { return address.get(); }
        public void setAddress(String address) { this.address.set(address); }

        public StringProperty emailProperty() { return email; }
        @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                message = "이메일주소 형식이 올바르지 않습니다.")
        public String getEmail() { return email.get(); }
        public void setEmail(String email) { this.email.set(email); }
    }

Register validator

    BeanValidationSupport validationSupport = new BeanValidationSupport();
    final Person person = new Person();
    validationSupport.setValidationDecorator(new GraphicValidationDecoration());

    person.nameProperty().bindBidirectional(nameTxt.textProperty());
    validationSupport.registerValidator(nameTxt, person, "name");
    person.addressProperty().bindBidirectional(addressTxt.textProperty());
    validationSupport.registerValidator(addressTxt, person, "address");
    person.emailProperty().bindBidirectional(emailTxt.textProperty());
    validationSupport.registerValidator(emailTxt, person, "email");
