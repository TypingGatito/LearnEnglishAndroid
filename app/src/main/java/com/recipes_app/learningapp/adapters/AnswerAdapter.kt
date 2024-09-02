package com.recipes_app.learningapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recipes_app.learningapp.R
import com.recipes_app.learningapp.questions.Answer

class AnswerAdapter(val answers: List<Answer>) : RecyclerView.Adapter<AnswerAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvQuestion)
        val tvUsersAnswer: TextView = view.findViewById(R.id.tvUsersAnswer)
        val tvRightAnswer: TextView = view.findViewById(R.id.tvRightAnswer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.answers_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvId.text = "Изначальный вопрос: " + answers[position].getQuestionWord()
        holder.tvUsersAnswer.text = "Ваш ответ: " + answers[position].getUsersAnswer()
        holder.tvRightAnswer.text = "Правильный ответ: " + answers[position].getRightAnswer()
    }

    override fun getItemCount(): Int {
        return answers.count()
    }
}