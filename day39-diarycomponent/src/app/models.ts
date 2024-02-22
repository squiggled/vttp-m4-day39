export interface Diary{
    date: Date,
    entry: string,
    type: "travel" | "climbing" | "hobbies" | "eating"
}

export interface DiarySlice{
    entries:Diary[]
}