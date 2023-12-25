
class handleDate{
    constructor(){
        this.timeNow = () => {
            let d = new Date();
            return d.toISOString();
        }
        this.toDateTime = (date) => {
            let d = new Date(date);
            return d.toLocaleString();
        }
        this.toDate = (date) => {
            let d = new Date(date);
            return d.toLocaleDateString();
        }
        this.toISOString = (date) => {
            let d = new Date(date);
            return d.toISOString();
        }
    }
}
export default handleDate;