package com.kaano8.androidcore.handler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaano8.androidcore.R
import com.kaano8.androidcore.databinding.FragmentHandlerBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HandlerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HandlerFragment : Fragment() {

    private lateinit var worker: Worker
    private lateinit var handler: Handler
    private lateinit var simpleLooper: SimpleLooper

    private lateinit var binding: FragmentHandlerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHandlerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHandler()

        // worked = SimpleWorker()
        worker = Worker()
        setupWorker()


    }

    private fun setupHandler() {
        handler = Handler(Looper.getMainLooper()) {
            binding.tvMessage.text = it.obj.toString()
            // if no more handling is required, return true
            true
        }
    }

    private fun setupWorker() {
        worker.execute {
            Thread.sleep(3_000)
            handler.sendMessage(Message.obtain().apply { obj = "Task 1 completed" })
        }.execute {
            Thread.sleep(2_000)
            handler.sendMessage(Message.obtain().apply { obj = "Task 2 completed" })
        }.execute {
            Thread.sleep(1_000)
            handler.sendMessage(Message.obtain().apply { obj = "Task 3 completed" })
        }
    }

    private fun setupSimpleLooper() {
        simpleLooper = SimpleLooper()
        simpleLooper.execute("Hello").execute("World")
    }
}